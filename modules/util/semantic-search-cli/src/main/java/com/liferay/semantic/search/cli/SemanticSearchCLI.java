/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.semantic.search.cli.command.Command;
import com.liferay.semantic.search.cli.command.IngestCommand;
import com.liferay.semantic.search.cli.command.QueryCommand;
import com.liferay.semantic.search.cli.command.SimilarCommand;
import com.liferay.semantic.search.cli.command.StatusCommand;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Entry point for the semantic-search CLI. Subcommands, flags, and the
 * JSON output schema are the stable interface; skills and agents shell
 * out to this jar by command.
 *
 * @author JR Houn
 */
public class SemanticSearchCLI {

	public static void main(String[] args) {
		Map<String, Command> commands =
			LinkedHashMapBuilder.<String, Command>put(
				"ingest", new IngestCommand()
			).put(
				"query", new QueryCommand()
			).put(
				"similar", new SimilarCommand()
			).put(
				"status", new StatusCommand()
			).build();

		if ((args.length == 0) || Objects.equals(args[0], "--help") ||
			Objects.equals(args[0], "-h")) {

			_printUsage(commands);

			System.exit((args.length == 0) ? 2 : 0);
		}

		String commandName = args[0];

		Command command = commands.get(commandName);

		if (command == null) {
			System.err.println("search: unknown command: " + commandName);
			System.err.println();

			_printUsage(commands);

			System.exit(2);
		}

		String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

		try {
			int exitCode = command.run(commandArgs);

			System.exit(exitCode);
		}
		catch (Exception exception) {
			System.err.println(
				"search: internal error: " + exception.getMessage());

			exception.printStackTrace(System.err);

			System.exit(6);
		}
	}

	private static void _printUsage(Map<String, Command> commands) {
		System.err.println("Usage: search <command> [args...]");
		System.err.println();
		System.err.println("Commands:");

		for (Map.Entry<String, Command> entry : commands.entrySet()) {
			Command command = entry.getValue();

			System.err.println(
				StringBundler.concat(
					"  ", entry.getKey(), "  ", command.description()));
		}
	}

}