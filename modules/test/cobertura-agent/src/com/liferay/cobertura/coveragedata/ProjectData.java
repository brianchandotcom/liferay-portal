/*
 * Cobertura - http://cobertura.sourceforge.net/
 *
 * Copyright (C) 2003 jcoverage ltd.
 * Copyright (C) 2005 Mark Doliner
 * Copyright (C) 2005 Grzegorz Lukasik
 * Copyright (C) 2005 Björn Beskow
 * Copyright (C) 2006 John Lewis
 * Copyright (C) 2009 Chris van Es
 * Copyright (C) 2009 Ed Randall
 * Copyright (C) 2010 Charlie Squires
 * Copyright (C) 2010 Piotr Tabor
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

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.CoverageData;
import net.sourceforge.cobertura.coveragedata.PackageData;

public class ProjectData extends CoverageDataContainer
{

	private static final long serialVersionUID = 6;

	/** This collection is used for quicker access to the list of classes. */
	private Map classes = new HashMap();

	public ClassData getClassData(String name)
	{
		lock.lock();
		try
		{
			return (ClassData)this.classes.get(name);
		}
		finally
		{
			lock.unlock();
		}
	}

	/**
	 * This is called by instrumented bytecode.
	 */
	public ClassData getOrCreateClassData(String name)
	{
		lock.lock();
		try
		{
			ClassData classData = (ClassData)this.classes.get(name);
			if (classData == null)
			{
				classData = new ClassData(name);
				String packageName = classData.getPackageName();
				PackageData packageData = (PackageData)children.get(packageName);
				if (packageData == null)
				{
					packageData = new PackageData(packageName);
					// Each key is a package name, stored as an String object.
					// Each value is information about the package, stored as a PackageData object.
					this.children.put(packageName, packageData);
				}
				packageData.addClassData(classData);
				this.classes.put(classData.getName(), classData);
			}
			return classData;
		}
		finally
		{
			lock.unlock();
		}
	}

	public void merge(CoverageData coverageData)
	{
		if (coverageData == null) {
			return;
		}
		ProjectData projectData = (ProjectData)coverageData;
		getBothLocks(projectData);
		try
		{
			super.merge(coverageData);

			for (Iterator iter = projectData.classes.keySet().iterator(); iter.hasNext();)
			{
				Object key = iter.next();
				if (!this.classes.containsKey(key))
				{
					this.classes.put(key, projectData.classes.get(key));
				}
			}
		}
		finally
		{
			lock.unlock();
			projectData.lock.unlock();
		}
	}

}
