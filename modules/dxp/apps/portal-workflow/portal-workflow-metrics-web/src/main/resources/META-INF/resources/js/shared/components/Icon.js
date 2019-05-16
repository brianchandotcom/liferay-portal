import getCN from 'classnames';
import React from 'react';

export default class Icon extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			elementClasses: props.elementClasses || '',
			iconName: props.iconName || ''
		};
	}

	componentWillReceiveProps(nextProps) {
		this.setState(nextProps);
	}

	render() {
		const { elementClasses, iconName } = this.state;
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