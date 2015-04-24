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

	private Map classes = new HashMap();

	public ClassData getClassData(String name)
	{
			return (ClassData)this.classes.get(name);
	}

	public ClassData getOrCreateClassData(String name)
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
					this.children.put(packageName, packageData);
				}
				packageData.addClassData(classData);
				this.classes.put(classData.getName(), classData);
			}
			return classData;
	}

	@Override
	public void merge(CoverageData coverageData)
	{
		if (coverageData == null) {
			return;
		}
		ProjectData projectData = (ProjectData)coverageData;
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

}
