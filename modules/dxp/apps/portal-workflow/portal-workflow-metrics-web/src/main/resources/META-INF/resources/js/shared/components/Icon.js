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
				focusable='false'
				role='presentation'
			/>
		);
	}
}
