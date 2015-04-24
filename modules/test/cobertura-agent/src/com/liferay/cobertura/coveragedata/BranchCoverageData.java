/*
 * Cobertura - http://cobertura.sourceforge.net/
 *
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

public interface BranchCoverageData
{

	double getBranchCoverageRate();

	int getNumberOfCoveredBranches();

	int getNumberOfValidBranches();

	/**
	 * Warning: This is generally implemented as a
	 * "shallow" merge.  For our current use, this
	 * should be fine, but in the future it may make
	 * sense to modify the merge methods of the
	 * various classes to do a deep copy of the
	 * appropriate objects.
	 */
	void merge(BranchCoverageData coverageData);
}
