/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const DEFAULT_APPEND_CONTENT = '&nbsp;&nbsp;';

class AutoSize {
	constructor(inputElement) {
		this.inputElement = inputElement;
		this.computedStyle = getComputedStyle(this.inputElement);

		this.minHeight = parseInt(
			this.computedStyle.height.replace('px', ''),
			10
		);

		this.template = this.createTemplate(this.computedStyle);
		document.body.appendChild(this.template);

		this.inputElement.addEventListener('input', this.handleInput);
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

	handleInput = event => {
		requestAnimationFrame(() => {
			const target = event.target;
			const value = target.value;

			if (this.template.style.width !== this.computedStyle.width) {
				this.template.style.width = this.computedStyle.width;
			}

			this.template.innerHTML = value + DEFAULT_APPEND_CONTENT;

			const height =
				this.template.scrollHeight < this.minHeight
					? this.minHeight
					: this.template.scrollHeight;

			target.style.height = height + 'px';
		});
	};
}

export default AutoSize;
