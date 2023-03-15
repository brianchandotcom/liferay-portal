import Button from 'shared/components/Button';
import Icon from 'shared/components/Icon';
import PropTypes from 'prop-types';
import React from 'react';

interface BackButtonIProps {
	href: string;
	label: string;
}

export default class BackButton extends React.Component<BackButtonIProps> {
	static propTypes = {
		href: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired
	};

	render() {
		const {href, label} = this.props;

		return (
			<div className='back-button-root'>
				<Button borderless display='secondary' href={href} outline>
					<Icon
						className='inline-item inline-item-before'
						size='sm'
						symbol='angle-left'
					/>

					{label}
				</Button>
			</div>
		);
	}
}
