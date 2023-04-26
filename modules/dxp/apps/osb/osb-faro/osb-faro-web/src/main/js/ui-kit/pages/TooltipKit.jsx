import ClayButton from '@clayui/button';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';

export default class TooltipKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<h3>{'Left'}</h3>

				<Row>
					<Item>
						<span
							data-tooltip
							data-tooltip-align='left'
							title='show tooltip'
						>
							{'Show tooltip left'}
						</span>
					</Item>
				</Row>

				<h3>{'Top'}</h3>

				<Row>
					<Item>
						<span
							data-tooltip
							data-tooltip-align='top-left'
							title='show tooltip'
						>
							{'Show tooltip top left'}
						</span>
					</Item>

					<Item>
						<span
							data-tooltip
							data-tooltip-align='top'
							title='show tooltip'
						>
							{'Show tooltip top'}
						</span>
					</Item>

					<Item>
						<span
							data-tooltip
							data-tooltip-align='top-right'
							title='show tooltip'
						>
							{'Show tooltip top right'}
						</span>
					</Item>
				</Row>

				<h3>{'Right'}</h3>

				<Row>
					<Item>
						<span
							data-tooltip
							data-tooltip-align='right'
							title='show tooltip'
						>
							{'Show tooltip right'}
						</span>
					</Item>
				</Row>

				<h3>{'Bottom'}</h3>

				<Row>
					<Item>
						<span
							data-tooltip
							data-tooltip-align='bottom-left'
							title='show tooltip'
						>
							{'Show tooltip bottom left'}
						</span>
					</Item>

					<Item>
						<span
							data-tooltip
							data-tooltip-align='bottom'
							title='show tooltip'
						>
							{'Show tooltip bottom'}
						</span>
					</Item>

					<Item>
						<span
							data-tooltip
							data-tooltip-align='bottom-right'
							title='show tooltip'
						>
							{'Show tooltip bottom right'}
						</span>
					</Item>
				</Row>

				<h3>{'Clicked Interaction'}</h3>

				<Row>
					<Item>
						<ClayButton
							className='button-root'
							data-tooltip-response='Clicked!'
							displayType='secondary'
							title='show tooltip'
						>
							{'Click to see response!'}
						</ClayButton>
					</Item>
				</Row>
			</div>
		);
	}
}
