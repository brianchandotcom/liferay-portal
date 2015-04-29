/*
 * Cobertura - http://cobertura.sourceforge.net/
 *
 * Copyright (C) 2003 jcoverage ltd.
 * Copyright (C) 2005 Mark Doliner
 * Copyright (C) 2006 Jiri Mares
 *
 * Cobertura is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * Cobertura is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cobertura; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package com.liferay.cobertura.coveragedata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * <p>
 * ProjectData information is typically serialized to a file. An
 * instance of this class records coverage information for a single
 * class that has been instrumented.
 * </p>
 *
 * <p>
 * This class implements HasBeenInstrumented so that when cobertura
 * instruments itself, it will omit this class.  It does this to
 * avoid an infinite recursion problem because instrumented classes
 * make use of this class.
 * </p>
 */

public class ClassData extends CoverageDataContainer
	implements Comparable<ClassData>
{

	private static final long serialVersionUID = 5;

	/**
	 * Each key is a line number in this class, stored as an Integer object.
	 * Each value is information about the line, stored as a LineData object.
	 */
	private Map<Integer,LineData> branches = new HashMap<Integer,LineData>();

	private String name = null;

	/**
	 * @param name In the format "net.sourceforge.cobertura.coveragedata.ClassData"
	 */
	public ClassData(String name)
	{
		this.name = name;
	}

	public LineData addLine(LineData lineData) {
		LineData previousLineData = (LineData)children.putIfAbsent(
			lineData.getLineNumber(), lineData);

		if (previousLineData == null) {
			return lineData;
		}

		return previousLineData;
	}

	/**
	 * This is required because we implement Comparable.
	 */
	public int compareTo(ClassData o)
	{
		return this.name.compareTo(o.name);
	}

	/**
	 * Returns true if the given object is an instance of the
	 * ClassData class, and it contains the same data as this
	 * class.
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || !(obj.getClass().equals(this.getClass())))
			return false;

		ClassData classData = (ClassData)obj;
			return super.equals(obj)
				&& this.branches.equals(classData.branches)
				&& this.name.equals(classData.name);
	}

	public String getBaseName()
	{
		int lastDot = this.name.lastIndexOf('.');
		if (lastDot == -1)
		{
			return this.name;
		}
		return this.name.substring(lastDot + 1);
	}

	public SortedSet<CoverageData> getLines()
	{
			return new TreeSet<CoverageData>(this.children.values());
	}

	public String getName()
	{
		return name;
	}

	/**
	 * @return The number of branches in this class.
	 */
	public int getNumberOfValidBranches()
	{
		int number = 0;
			for (Iterator<LineData> i = branches.values().iterator();
				i.hasNext();
				number += (i.next()).getNumberOfValidBranches())
				;
			return number;
	}

	/**
	 * @see net.sourceforge.cobertura.coveragedata.CoverageData#getNumberOfCoveredBranches()
	 */
	public int getNumberOfCoveredBranches()
	{
		int number = 0;
			for (Iterator<LineData> i = branches.values().iterator();
				i.hasNext();
				number += (i.next()).getNumberOfCoveredBranches())
				;
			return number;
	}

	public String getPackageName()
	{
		int lastDot = this.name.lastIndexOf('.');
		if (lastDot == -1)
		{
			return "";
		}
		return this.name.substring(0, lastDot);
	}

	public int hashCode()
	{
		return this.name.hashCode();
	}

	public void addLineJump(int lineNumber, int branchNumber) {
		LineData lineData = (LineData)children.get(Integer.valueOf(lineNumber));

		if (lineData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + name + " line " +
					lineNumber);
		}

		lineData.addJump(new JumpData(branchNumber));

		branches.put(lineNumber, lineData);
	}

	public void addLineSwitch(int lineNumber, int switchNumber, int[] keys)
	{
			LineData lineData = (LineData)children.get(Integer.valueOf(lineNumber));
			if (lineData != null)
			{
				lineData.addSwitch(switchNumber, keys);
				this.branches.put(Integer.valueOf(lineNumber), lineData);
			}
	}

	public void addLineSwitch(int lineNumber, int switchNumber, int min, int max)
	{
			LineData lineData = (LineData)children.get(Integer.valueOf(lineNumber));
			if (lineData != null)
			{
				lineData.addSwitch(switchNumber, min, max);
				this.branches.put(Integer.valueOf(lineNumber), lineData);
			}
	}

	/**
	 * Merge some existing instrumentation with this instrumentation.
	 *
	 * @param coverageData Some existing coverage data.
	 */
	public void merge(CoverageData coverageData)
	{
		ClassData classData = (ClassData)coverageData;

		// If objects contain data for different classes then don't merge
		if (!this.getName().equals(classData.getName()))
			return;

			super.merge(coverageData);

			// We can't just call this.branches.putAll(classData.branches);
			// Why not?  If we did a putAll, then the LineData objects from
			// the coverageData class would overwrite the LineData objects
			// that are already in "this.branches"  And we don't need to
			// update the LineData objects that are already in this.branches
			// because they are shared between this.branches and this.children,
			// so the object hit counts will be moved when we called
			// super.merge() above.
			for (Iterator<Integer> iter = classData.branches.keySet().iterator(); iter.hasNext();)
			{
				Integer key = iter.next();
				if (!this.branches.containsKey(key))
				{
					this.branches.put(key, classData.branches.get(key));
				}
			}
	}

	/**
	 * Increment the number of hits for a particular line of code.
	 *
	 * @param lineNumber the line of code to increment the number of hits.
	 * @param hits how many times the piece was called
	 */
	public void touch(int lineNumber,int hits)
	{
			LineData lineData = (LineData)children.get(Integer.valueOf(lineNumber));
			if (lineData == null) {
				throw new IllegalStateException(
					"No instrument data for class " + name + " line " +
						lineNumber);
			}
			lineData.touch(hits);
	}

	/**
	 * Increments the number of hits for particular hit counter of particular branch on particular line number.
	 *
	 * @param lineNumber The line of code where the branch is
	 * @param branchNumber  The branch on the line to change the hit counter
	 * @param branch The hit counter (true or false)
	 * @param hits how many times the piece was called
	 */
	public void touchJump(int lineNumber, int branchNumber, boolean branch,int hits) {
			LineData lineData = (LineData)children.get(Integer.valueOf(lineNumber));
			if (lineData == null) {
				throw new IllegalStateException(
					"No instrument data for class " + name + " line " +
						lineNumber);
			}
			lineData.touchJump(name, branchNumber, branch,hits);
	}

	/**
	 * Increments the number of hits for particular hit counter of particular switch branch on particular line number.
	 *
	 * @param lineNumber The line of code where the branch is
	 * @param switchNumber  The switch on the line to change the hit counter
	 * @param branch The hit counter
	 * @param hits how many times the piece was called
	 */
	public void touchSwitch(int lineNumber, int switchNumber, int branch,int hits) {
			LineData lineData = (LineData)children.get(Integer.valueOf(lineNumber));
			if (lineData == null) {
				throw new IllegalStateException(
					"No instrument data for class " + name + " line " +
						lineNumber);
			}
			lineData.touchSwitch(switchNumber, branch,hits);
	}

}
