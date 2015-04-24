package com.liferay.cobertura.coveragedata;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sourceforge.cobertura.coveragedata.CoverageData;

public abstract class CoverageDataContainer
	implements CoverageData, Serializable
{

	private static final long serialVersionUID = 2;

	Map<Object,CoverageData> children = new HashMap<Object,CoverageData>();

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if ((obj == null) || !(obj.getClass().equals(this.getClass())))
			return false;

		CoverageDataContainer coverageDataContainer = (CoverageDataContainer)obj;
			return this.children.equals(coverageDataContainer.children);
	}

	@Override
	public double getBranchCoverageRate()
	{
		int number = 0;
		int numberCovered = 0;
			Iterator<CoverageData> iter = this.children.values().iterator();
			while (iter.hasNext())
			{
				CoverageData coverageContainer = iter.next();
				number += coverageContainer.getNumberOfValidBranches();
				numberCovered += coverageContainer.getNumberOfCoveredBranches();
			}
		if (number == 0)
		{
			return 1d;
		}
		return (double)numberCovered / number;
	}

	@Override
	public double getLineCoverageRate()
	{
		int number = 0;
		int numberCovered = 0;
			Iterator<CoverageData> iter = this.children.values().iterator();
			while (iter.hasNext())
			{
				CoverageData coverageContainer = iter.next();
				number += coverageContainer.getNumberOfValidLines();
				numberCovered += coverageContainer.getNumberOfCoveredLines();
			}
		if (number == 0)
		{
			return 1d;
		}
		return (double)numberCovered / number;
	}

	@Override
	public int getNumberOfCoveredBranches()
	{
		int number = 0;
			Iterator<CoverageData> iter = this.children.values().iterator();
			while (iter.hasNext())
			{
				CoverageData coverageContainer = iter.next();
				number += coverageContainer.getNumberOfCoveredBranches();
			}
		return number;
	}

	@Override
	public int getNumberOfCoveredLines()
	{
		int number = 0;
			Iterator<CoverageData> iter = this.children.values().iterator();
			while (iter.hasNext())
			{
				CoverageData coverageContainer = iter.next();
				number += coverageContainer.getNumberOfCoveredLines();
			}
		return number;
	}

	@Override
	public int getNumberOfValidBranches()
	{
		int number = 0;
			Iterator<CoverageData> iter = this.children.values().iterator();
			while (iter.hasNext())
			{
				CoverageData coverageContainer = iter.next();
				number += coverageContainer.getNumberOfValidBranches();
			}
		return number;
	}

	@Override
	public int getNumberOfValidLines()
	{
		int number = 0;
			Iterator<CoverageData> iter = this.children.values().iterator();
			while (iter.hasNext())
			{
				CoverageData coverageContainer = iter.next();
				number += coverageContainer.getNumberOfValidLines();
			}
		return number;
	}

	@Override
	public int hashCode()
	{
			return this.children.size();
	}

	@Override
	public void merge(CoverageData coverageData)
	{
		CoverageDataContainer container = (CoverageDataContainer)coverageData;
			Iterator<Object> iter = container.children.keySet().iterator();
			while (iter.hasNext())
			{
				Object key = iter.next();
				CoverageData newChild = (CoverageData)container.children.get(key);
				CoverageData existingChild = (CoverageData)this.children.get(key);
				if (existingChild != null)
				{
					existingChild.merge(newChild);
				}
				else
				{
					this.children.put(key, newChild);
				}
			}
	}

}