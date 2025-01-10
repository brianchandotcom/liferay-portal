/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ElementType} from 'react';

type RawDOMProps = {
	TagName: ElementType;
	elementRef:
		| React.MutableRefObject<HTMLElement>
		| React.RefCallback<HTMLElement>;
};

/**
 * Creates a DOM node that will be kept forever
 * to allow manipulating the DOM manually.
 */
export default class RawDOM extends React.Component<RawDOMProps> {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		const TagName = this.props.TagName;

		return <TagName ref={this.props.elementRef} />;
	}
}
