/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Transformer = (markdown: string) => string;

function apply(markdown: string, ...transformers: Transformer[]) {
	return transformers.reduce(
		(current, transformer) => transformer(current),
		markdown
	);
}

export function escapeReservedCharacters(markdown: string) {
	return markdown
		.replace(/&/g, '&amp;')
		.replace(/</g, '&lt;')
		.replace(/>/g, '&gt;');
}

export function normalizeInlineHeaders(markdown: string) {
	return markdown
		.replace(/([^\n])\s*(#{1,6})\s+/g, '$1\n\n$2 ')
		.replace(/\s*(#{1,6})\s+([^\n]+)/g, '\n\n$1 $2\n\n');
}

export function renderAIAssistantMessageMarkdown(markdown: string) {
	return apply(
		normalizeInlineHeaders(markdown),
		escapeReservedCharacters,
		renderHeaders,
		renderInlineFormatting,
		renderLists,
		wrapParagraphs
	);
}

export function renderHeaders(markdown: string) {
	return markdown
		.replace(/^### (.*)$/gm, '<h3>$1</h3>')
		.replace(/^## (.*)$/gm, '<h2>$1</h2>')
		.replace(/^# (.*)$/gm, '<h1>$1</h1>');
}

export function renderInlineFormatting(markdown: string) {
	return markdown
		.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
		.replace(/\*(.*?)\*/g, '<em>$1</em>')
		.replace(/`([^`]+)`/g, '<code>$1</code>');
}

export function renderLists(markdown: string) {
	return markdown.replace(/(^|\n)- (.*)(\n- .*)*/g, (match) => {
		const items = match
			.trim()
			.split('\n')
			.map((line) => `<li>${line.replace(/^- /, '')}</li>`)
			.join('');

		return `<ul>${items}</ul>`;
	});
}

export function wrapParagraphs(markdown: string) {
	return markdown
		.split(/\n{2,}/)
		.map((block) => {
			if (block.match(/^<(blockquote|h\d|ol|p|ul)/)) {
				return block;
			}

			return `<p>${block.replace(/\n/g, '<br />')}</p>`;
		})
		.join('');
}

export default renderAIAssistantMessageMarkdown;
