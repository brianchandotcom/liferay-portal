import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import PropTypes from 'prop-types';
import React from 'react';

interface ISpinnerProps extends React.HTMLAttributes<HTMLDivElement> {
	alignCenter?: boolean;
	display: string;
	fadeIn: boolean;
	inline: boolean;
	light: boolean;
	overlay: boolean;
	size?: string;
	spacer: boolean;
}

export default class Spinner extends React.Component<ISpinnerProps> {
	static DISPLAYS: string[] = ['dotted'];
	static SIZES: string[] = ['sm', 'default'];

	static defaultProps = {
		alignCenter: false,
		display: Spinner.DISPLAYS[0],
		fadeIn: false,
		inline: false,
		light: false,
		overlay: false,
		spacer: false
	};

	static propTypes = {
		alignCenter: PropTypes.bool,
		display: PropTypes.oneOf(Spinner.DISPLAYS),
		fadeIn: PropTypes.bool,
		inline: PropTypes.bool,
		light: PropTypes.bool,
		overlay: PropTypes.bool,
		size: PropTypes.oneOf(Spinner.SIZES),
		spacer: PropTypes.bool
	};

	render() {
		const {
			alignCenter,
			className,
			display,
			fadeIn,
			inline,
			light,
			overlay,
			size,
			spacer,
			...otherProps
		} = this.props;

		const classes = getCN('spinner-root', className, {
			'spinner-center': alignCenter,
			'spinner-fade-in': fadeIn,
			'spinner-inline': inline,
			'spinner-overlay': overlay,
			'spinner-spacer': spacer
		});

		const animationClasses = getCN('loading-animation', {
			'loading-animation-light': light,
			[`loading-animation-${display}`]: display,
			[`loading-animation-${size}`]: size
		});

		return (
			<div
				{...omitDefinedProps(otherProps, Spinner.propTypes)}
				className={classes}
			>
				<span className={animationClasses} />
			</div>
		);
	}
}
