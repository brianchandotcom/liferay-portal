import getCN from 'classnames';
import React from 'react';

export default class Icon extends React.Component {
	render() {
		const { elementClasses, iconName } = this.props;
		const classes = getCN(
			'lexicon-icon',
			`lexicon-icon-${iconName}`,
			elementClasses
		);

		return (
			<svg className={classes} focusable="false" role="presentation">
				<use
					href={`${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg#${iconName}`}
				/>
			</svg>
		);
	}
}