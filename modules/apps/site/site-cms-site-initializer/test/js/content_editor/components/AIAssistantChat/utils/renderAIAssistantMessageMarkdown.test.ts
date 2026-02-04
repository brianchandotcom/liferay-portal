/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	escapeReservedCharacters,
	normalizeInlineHeaders,
	renderAIAssistantMessageMarkdown,
	renderHeaders,
	renderInlineFormatting,
	renderLists,
	wrapParagraphs,
} from '../../../../../../src/main/resources/META-INF/resources/js/content_editor/components/AIAssistantChat/utils/renderAIAssistantMessageMarkdown';

describe('renderAIAssistantMessageMarkdown utils', () => {
	it('escapes reserved characters', () => {
		expect(escapeReservedCharacters('<div>&</div>')).toBe(
			'&lt;div&gt;&amp;&lt;/div&gt;'
		);
	});

	it('normalizes inline headers into standalone blocks', () => {
		expect(normalizeInlineHeaders('Intro # Title')).toBe(
			'Intro\n\n# Title\n\n'
		);
	});

	it('renders full markdown with lists and escaping', () => {
		expect(renderAIAssistantMessageMarkdown('Hello\n\n- One\n- Two')).toBe(
			'<p>Hello<br /><ul><li>One</li><li>Two</li></ul></p>'
		);

		expect(renderAIAssistantMessageMarkdown('Hello <tag>')).toBe(
			'<p>Hello &lt;tag&gt;</p>'
		);
	});

	it('renders inline formatting', () => {
		expect(renderInlineFormatting('**bold** *em* `code`')).toBe(
			'<strong>bold</strong> <em>em</em> <code>code</code>'
		);
	});

	it('renders markdown headers', () => {
		expect(renderHeaders('# One\n## Two\n### Three')).toBe(
			'<h1>One</h1>\n<h2>Two</h2>\n<h3>Three</h3>'
		);
	});

	it('renders unordered lists', () => {
		expect(renderLists('- a\n- b')).toBe('<ul><li>a</li><li>b</li></ul>');
	});

	it('wraps paragraphs and preserves block elements', () => {
		expect(
			wrapParagraphs(
				'<h2>Title</h2>\n\nParagraph line1\nline2\n\n<ul><li>a</li></ul>'
			)
		).toBe(
			'<h2>Title</h2><p>Paragraph line1<br />line2</p><ul><li>a</li></ul>'
		);
	});
});
