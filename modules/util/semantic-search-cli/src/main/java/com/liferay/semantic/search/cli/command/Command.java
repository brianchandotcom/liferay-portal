/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.command;

/**
 * Contract for every CLI subcommand.
 *
 * Exit codes:
 *   0  success
 *   1  bad input (no .md files under target, etc.)
 *   2  bad CLI usage
 *   3  index does not exist
 *   4  target file has no extractable content
 *   5  Qdrant unreachable
 *   6  internal error reading the index
 *
 * @author JR Houn
 */
public interface Command {

	public String description();

	public int run(String[] args) throws Exception;

}