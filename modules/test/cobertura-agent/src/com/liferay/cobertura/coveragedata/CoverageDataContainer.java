package com.liferay.cobertura.coveragedata;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import java.util.Map.Entry;
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

		for (CoverageData coverageData : children.values()) {
			number += coverageData.getNumberOfValidBranches();
			numberCovered += coverageData.getNumberOfCoveredBranches();
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

		for (CoverageData coverageData : children.values()) {
			number += coverageData.getNumberOfValidLines();
			numberCovered += coverageData.getNumberOfCoveredLines();
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

		for (CoverageData coverageData : children.values()) {
			number += coverageData.getNumberOfCoveredBranches();
		}

		return number;
	}

	@Override
	public int getNumberOfCoveredLines()
	{
		int number = 0;

		for (CoverageData coverageData : children.values()) {
			number += coverageData.getNumberOfCoveredLines();
		}

		return number;
	}

	@Override
	public int getNumberOfValidBranches()
	{
		int number = 0;

		for (CoverageData coverageData : children.values()) {
			number += coverageData.getNumberOfValidBranches();
		}

		return number;
	}

	@Override
	public int getNumberOfValidLines()
	{
		int number = 0;

		for (CoverageData coverageData : children.values()) {
			number += coverageData.getNumberOfValidLines();
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

		Map<Object, CoverageData> otherChildren = container.children;

		for (Entry<Object, CoverageData> entry : otherChildren.entrySet()) {
			Object key = entry.getKey();

			CoverageData otherChild = entry.getValue();

			CoverageData myChild = children.get(key);

			if (myChild == null) {
				children.put(key, otherChild);
			}
			else {
				myChild.merge(otherChild);
			}
		}
	}

}