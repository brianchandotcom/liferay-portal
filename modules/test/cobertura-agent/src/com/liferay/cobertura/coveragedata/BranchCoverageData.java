package com.liferay.cobertura.coveragedata;

public interface BranchCoverageData
{

	double getBranchCoverageRate();

	int getNumberOfCoveredBranches();

	int getNumberOfValidBranches();

	void merge(BranchCoverageData coverageData);
}
