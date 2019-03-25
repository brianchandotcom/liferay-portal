import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
export default class Icon extends React.Component {
	render() {
		const { iconName } = this.props;

		return (
			<svg
				className={`lexicon-icon lexicon-icon-${iconName}`}
				focusable="false"
				role="presentation"
			>
				<use
					href={`${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg#${iconName}`}
				/>
			</svg>
		);
	}
}