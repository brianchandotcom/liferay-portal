package com.liferay.cobertura.coveragedata;

public interface CoverageData
{

	double getBranchCoverageRate();

	double getLineCoverageRate();

	int getNumberOfCoveredBranches();

	int getNumberOfCoveredLines();

	int getNumberOfValidBranches();

	int getNumberOfValidLines();

	void merge(CoverageData coverageData);
}
