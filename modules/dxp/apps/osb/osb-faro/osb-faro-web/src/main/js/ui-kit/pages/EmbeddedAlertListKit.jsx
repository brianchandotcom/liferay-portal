import EmbeddedAlertList from 'shared/components/EmbeddedAlertList';
import React from 'react';

export default class EmbeddedAlertListKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<EmbeddedAlertList
					alerts={[
						{
							iconSymbol: 'exclamation-full',
							message: 'foo bar',
							title: 'Test Title',
							type: 'danger'
						},
						{
							iconSymbol: 'exclamation-full',
							message: 'foo bar 2',
							title: 'Test Title 2',
							type: 'danger'
						}
					]}
				/>
			</div>
		);
	}
}
