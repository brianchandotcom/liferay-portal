import ErrorDisplay from 'shared/components/ErrorDisplay';
import React from 'react';
import {noop} from 'lodash';

export default class ErrorDisplayKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<div>
					<h3>{'ErrorDisplay'}</h3>

					<ErrorDisplay />
				</div>

				<div>
					<h3>{'ErrorDisplay w/ button'}</h3>

					<ErrorDisplay onReload={noop} />
				</div>

				<div>
					<h3>{'ErrorDisplay w/ button and spacer'}</h3>

					<ErrorDisplay onReload={noop} spacer />
				</div>
			</div>
		);
	}
}
