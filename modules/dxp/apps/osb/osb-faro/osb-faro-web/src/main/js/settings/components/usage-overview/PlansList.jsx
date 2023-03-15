import Panel from 'shared/components/Panel';
import PlanBreakdown from './PlanBreakdown';
import React from 'react';
import SubscriptionTitle from './SubscriptionTitle';
import {
	getPlanAddOns,
	getPropLabel,
	INDIVIDUALS,
	PAGEVIEWS,
	PLAN_TYPES,
	PLANS
} from 'shared/util/subscriptions';
import {PropTypes} from 'prop-types';

class PlansList extends React.Component {
	static defaultProps = {
		plans: []
	};

	static propTypes = {
		currentPlanName: PropTypes.string,
		plans: PropTypes.arrayOf(
			PropTypes.shape({
				baseSubscriptionPlan: PropTypes.string,
				limits: PropTypes.shape({
					[INDIVIDUALS]: PropTypes.number,
					[PAGEVIEWS]: PropTypes.number
				}),
				name: PropTypes.string,
				price: PropTypes.number
			})
		)
	};

	render() {
		const {currentPlanName, plans} = this.props;

		return (
			<div
				className={`plans-list-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				{plans.map(({limits, name, price}, index) => {
					const isCurrentPlan = currentPlanName === name;

					return (
						<Panel
							className={
								this.props.className
									? ` ${this.props.className}`
									: ''
							}
							expandable
							initialExpanded={index === 0}
							key={name}
							title={
								<SubscriptionTitle
									labelText={
										isCurrentPlan
											? Liferay.Language.get(
													'current-plan'
											  )
											: ''
									}
									name={getPropLabel(name)}
									price={!isCurrentPlan ? price : -1}
								/>
							}
						>
							<PlanBreakdown
								addOns={
									name === PLANS.basic.name
										? []
										: getPlanAddOns(PLAN_TYPES[name])
								}
								limits={[
									{
										entityLabel: INDIVIDUALS,
										value: limits.individuals
									},
									{
										entityLabel: PAGEVIEWS,
										value: limits.pageViews
									}
								]}
							/>
						</Panel>
					);
				})}
			</div>
		);
	}
}

export default PlansList;
