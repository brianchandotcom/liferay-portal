package com.liferay.cobertura.coveragedata;

import java.util.Iterator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.CoverageData;
import net.sourceforge.cobertura.coveragedata.PackageData;

public class ProjectData extends CoverageDataContainer
{

	private static final long serialVersionUID = 6;

	private final ConcurrentMap<String, ClassData> _classDataMap =
		new ConcurrentHashMap<>();

	public ClassData getClassData(String name)
	{
			return _classDataMap.get(name);
	}

	public ClassData getOrCreateClassData(String name)
	{
			ClassData classData = _classDataMap.get(name);
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
				_classDataMap.put(classData.getName(), classData);
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

			for (Iterator<String> iter = projectData._classDataMap.keySet().iterator(); iter.hasNext();)
			{
				String key = iter.next();
				if (!_classDataMap.containsKey(key))
				{
					_classDataMap.put(key, projectData._classDataMap.get(key));
				}
			}
	}

}
