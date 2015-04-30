/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.cobertura.coveragedata;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Shuyang Zhou
 */
public class ClassData
	extends CoverageDataContainer<Integer, LineData, ClassData> {

	private static final long serialVersionUID = 5;

	private String name = null;

	public ClassData(String name)
	{
		this.name = name;
	}

	public LineData addLine(LineData lineData) {
		LineData previousLineData = children.putIfAbsent(
			lineData.getLineNumber(), lineData);

		if (previousLineData == null) {
			return lineData;
		}

		return previousLineData;
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

	public Set<LineData> getLines() {
		Set<LineData> set = new TreeSet<>(
			new Comparator<LineData>() {

				@Override
				public int compare(LineData lineData1, LineData lineData2) {
					return lineData1.getLineNumber() -
						lineData2.getLineNumber();
				}

			});

		set.addAll(children.values());

		return set;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public int getNumberOfValidBranches()
	{
		int number = 0;
			for (Iterator<LineData> i = children.values().iterator();
				i.hasNext();
				number += (i.next()).getNumberOfValidBranches())
				;
			return number;
	}

	@Override
	public int getNumberOfCoveredBranches()
	{
		int number = 0;
			for (Iterator<LineData> i = children.values().iterator();
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

	public void addLineJump(int lineNumber, int branchNumber) {
		LineData lineData = children.get(lineNumber);

		if (lineData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + name + " line " +
					lineNumber);
		}

		lineData.addJump(new JumpData(branchNumber));
	}

	public void addLineSwitch(int lineNumber, int switchNumber, int[] keys) {
		LineData lineData = children.get(lineNumber);

		if (lineData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + name + " line " +
					lineNumber);
		}

		lineData.addSwitch(new SwitchData(switchNumber, keys.length));
	}

	public void addLineSwitch(
		int lineNumber, int switchNumber, int min, int max) {

		LineData lineData = children.get(lineNumber);

		if (lineData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + name + " line " +
					lineNumber);
		}

		lineData.addSwitch(new SwitchData(switchNumber, max - min + 1));
	}

	@Override
	public void merge(ClassData classData) {
		if (!this.getName().equals(classData.getName()))
			return;

			super.merge(classData);
	}

	public void touch(int lineNumber,int hits)
	{
			LineData lineData = children.get(lineNumber);
			if (lineData == null) {
				throw new IllegalStateException(
					"No instrument data for class " + name + " line " +
						lineNumber);
			}
			lineData.touch(hits);
	}

	public void touchJump(int lineNumber, int branchNumber, boolean branch,int hits) {
			LineData lineData = children.get(lineNumber);
			if (lineData == null) {
				throw new IllegalStateException(
					"No instrument data for class " + name + " line " +
						lineNumber);
			}
			lineData.touchJump(name, branchNumber, branch,hits);
	}

	public void touchSwitch(int lineNumber, int switchNumber, int branch,int hits) {
			LineData lineData = children.get(lineNumber);
			if (lineData == null) {
				throw new IllegalStateException(
					"No instrument data for class " + name + " line " +
						lineNumber);
			}
			lineData.touchSwitch(name, switchNumber, branch,hits);
	}

}
