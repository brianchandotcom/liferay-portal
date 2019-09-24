/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import getCN from 'classnames';
import React from 'react';

export default class Icon extends React.Component {
	render() {
		const {elementClasses, iconName} = this.props;
		const classes = getCN(
			'lexicon-icon',
			`lexicon-icon-${iconName}`,
			elementClasses
		);

		const useTag = `<use xlink:href="${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg#${iconName}" />`;

		return (
			<svg
				className={classes}
				dangerouslySetInnerHTML={{__html: useTag}}
				data-testid="icon"
				focusable="false"
				role="presentation"
			/>
		);
	}
}
