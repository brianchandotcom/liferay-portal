import { spawn } from 'child_process';

import * as vscode from 'vscode';

import { rgPath } from '@vscode/ripgrep';

export interface RipgrepArgs {
	args?: string[];
	globs?: string[];
	paths: string[];
	search: string;
}

export interface RipgrepMatch {
	location: vscode.Location;
	fullMatchText: string;
	captures: string[];
}

export async function ripgrep(ripgrepArgs: RipgrepArgs): Promise<string[]> {
	const { args = [], globs = [], paths, search } = ripgrepArgs;

	const totalArgs = [...args];

	for (const glob of globs) {
		totalArgs.push('--glob', glob);
	}

	const ripgrepProcess = spawn(
		rgPath,
		totalArgs.concat([search, '--', ...paths]),
	);

	let text = '';
	for await (const chunk of ripgrepProcess.stdout) {
		text += chunk;
	}

	return text.trim().split('\n');
}

interface RipgrepMatchData {
	path: {
		text: string;
	};
	lines: {
		text: string;
	};
	['line_number']: number;
	submatches: {
		match: {
			text: string;
		};
		start: number;
	}[];
}

export async function ripgrepMatches(
	ripgrepArgs: RipgrepArgs,
): Promise<RipgrepMatch[]> {
	const { args = [] } = ripgrepArgs;
	const lines = await ripgrep({
		...ripgrepArgs,
		args: [...args, '--json'],
	});

	const regex = new RegExp(ripgrepArgs.search);

	const results: RipgrepMatch[] = [];

	for (const line of lines) {
		const obj = JSON.parse(line);

		if (obj.type !== 'match') {
			continue;
		}

		const data: RipgrepMatchData = obj.data;

		const filePath: string = data.path.text;
		const lineNumber: number = Number(data.line_number);
		const lineText: string = data.lines.text;

		for (const submatch of data.submatches) {
			let columnNumber = Number(submatch.start);
			const submatchText: string = submatch.match.text;

			const match = submatchText.match(regex);

			if (match && match.length === 1) {
				columnNumber = lineText.indexOf(match[0]);
			} else if (match && match.length > 1) {
				columnNumber = lineText.indexOf(match[1]);
			}

			results.push({
				location: new vscode.Location(
					vscode.Uri.file(filePath),
					new vscode.Position(lineNumber - 1, columnNumber),
				),
				fullMatchText: submatchText,
				captures: match ? match.slice(1) : [],
			});
		}
	}

	return results;
}
