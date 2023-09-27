/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {escapeHTML} from './../util/html_util';

const DEFAULT_APPEND_CONTENT = '&nbsp;&nbsp;';

class AutoSize {
	constructor(inputElement) {
		this.inputElement = inputElement;
		this.computedStyle = getComputedStyle(this.inputElement);

		this.minHeight = parseInt(
			this.computedStyle.height.replace('px', ''),
			10
		);

		this.paddingHeight =
			parseInt(this.computedStyle.paddingTop.replace('px', ''), 10) +
			parseInt(this.computedStyle.paddingBottom.replace('px', ''), 10);

		this.template = this.createTemplate(this.computedStyle);
		document.body.appendChild(this.template);

		this.inputElement.addEventListener('input', this.handleInput);
		this._resizeInput(this.inputElement);
	}

	createTemplate(computedStyle) {
		const template = document.createElement('pre');

		template.style.clip = 'rect(0, 0, 0, 0) !important';
		template.style.left = '0';
		template.style.overflowWrap = 'break-word';
		template.style.position = 'absolute';
		template.style.top = '0';
		template.style.transform = 'scale(0)';
		template.style.whiteSpace = 'pre-wrap';
		template.style.wordBreak = 'break-word';

		template.style.fontFamily = computedStyle.fontFamily;
		template.style.fontSize = computedStyle.fontSize;
		template.style.fontStyle = computedStyle.fontStyle;
		template.style.fontWeight = computedStyle.fontWeight;
		template.style.lineHeight = computedStyle.lineHeight;
		template.style.letterSpacing = computedStyle.letterSpacing;
		template.style.textTransform = computedStyle.textTransform;

		template.style.width = computedStyle.width;

		template.textContent = DEFAULT_APPEND_CONTENT;

		return template;
	}

	handleInput = (event) => {
		requestAnimationFrame(() => {
			this._resizeInput(event.target);
		});
	};

	_resizeInput(inputElement) {
		if (this.template.style.width !== this.computedStyle.width) {
			this.template.style.width = this.computedStyle.width;
		}

		this.template.innerHTML =
			escapeHTML(inputElement.value) + DEFAULT_APPEND_CONTENT;

		inputElement.style.height = `${
			this.template.scrollHeight + this.paddingHeight < this.minHeight
				? this.minHeight
				: this.template.scrollHeight + this.paddingHeight
		}px`;
	}
}

export default AutoSize;
