import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import Panel from 'shared/components/Panel';
import React from 'react';
import SubscriptionTitle from './SubscriptionTitle';
import {
	getPlanAddOns,
	getPropIcon,
	getPropLabel,
	INDIVIDUALS,
	PAGEVIEWS,
	PLAN_TYPES
} from 'shared/util/subscriptions';
import {Plan} from 'shared/util/records';
import {PropTypes} from 'prop-types';

export default class AddOnsList extends React.Component {
	static defaultProps = {
		active: true
	};

	static propTypes = {
		active: PropTypes.bool,
		currentPlan: PropTypes.instanceOf(Plan),
		planType: PropTypes.string.isRequired
	};

	render() {
		const {active, className, currentPlan, planType} = this.props;

		const addOns = getPlanAddOns(planType);

		const classes = getCN('addons-list-root', className, {
			inactive: !active
		});

		return (
			<div className={classes}>
				{addOns.map(({limits, name, price}) => {
					const addOnLimit = [
						{
							entityLabel: INDIVIDUALS,
							value: limits[INDIVIDUALS]
						},
						{
							entityLabel: PAGEVIEWS,
							value: limits[PAGEVIEWS]
						}
					].find(({value}) => value);

					const addOnQuantity = currentPlan.addOns.getIn(
						[PLAN_TYPES[name], 'quantity'],
						0
					);

					const icon = getPropIcon(addOnLimit.entityLabel);

					return (
						<Panel key={name}>
							<SubscriptionTitle
								labelText={
									addOnQuantity ? `${addOnQuantity}X` : ''
								}
								name={
									<span>
										<ClayIcon
											className='icon-root'
											symbol={icon}
										/>

										<span className='limit-amount semibold'>
											<b className='text-secondary'>
												{'+'}
											</b>

											{addOnLimit.value.toLocaleString()}
										</span>

										<span className='text-secondary'>
											<b className='details-label'>
												{getPropLabel(
													addOnLimit.entityLabel
												)}
											</b>
										</span>
									</span>
								}
								price={active ? price : -1}
							/>
						</Panel>
					);
				})}
			</div>
		);
	}
}
