import Button from 'shared/components/Button';
import getCN from 'classnames';
import Icon from 'shared/components/Icon';
import React, {createRef} from 'react';
import ScrollableSection from 'shared/components/ScrollableSection';
import {DistributionTab} from 'shared/util/records';
import {List} from 'immutable';

interface ITabItemProps {
	active: boolean;
	disabled: boolean;
	id: string;
	onDelete: (id: string) => void;
	onSelect: (id: string) => void;
	title: string;
}

export type ITab = {
	context: string;
	id: string;
	numberOfBins: number;
	propertyId: string;
	propertyType: string;
	title: string;
};

const TabItem: React.FC<ITabItemProps> = ({
	active,
	disabled,
	id,
	onDelete,
	onSelect,
	title
}) => (
	<li className={getCN('tab-item d-flex align-items-baseline', {active})}>
		{!active && (
			<Button
				className='tab-title'
				disabled={disabled}
				display='unstyled'
				onClick={() => onSelect(id)}
			>
				{title}
			</Button>
		)}

		{active && (
			<div className='tab-title active'>
				{title}

				<Button
					className='remove-tab'
					display='unstyled'
					onClick={() => onDelete(id)}
					size='sm'
				>
					<Icon monospaced size='sm' symbol='times' />
				</Button>
			</div>
		)}
	</li>
);

interface ITabsProps {
	itemsIList: List<DistributionTab>;
	onAdd: () => void;
	onDelete: (id: string) => void;
	onSelect: (id: string) => void;
	selectedTabIndex: number;
	showAddProperty: boolean;
}

export default class Tabs extends React.Component<ITabsProps> {
	static defaultProps = {
		itemsIList: List()
	};

	private _scrollableSectionRef = createRef<ScrollableSection>();

	componentDidUpdate(prevProps) {
		const {itemsIList} = this.props;

		const count = itemsIList.size;
		const prevCount = prevProps.itemsIList.size;

		if (this._scrollableSectionRef && prevCount) {
			const {scrollToBeg, scrollToEnd} =
				this._scrollableSectionRef.current;

			if (count > prevCount) {
				scrollToEnd();
			} else if (count < prevCount) {
				scrollToBeg();
			}
		}
	}

	render() {
		const {
			itemsIList,
			onAdd,
			onDelete,
			onSelect,
			selectedTabIndex,
			showAddProperty
		} = this.props;

		return (
			<div className='tabs-root d-flex align-items-center justify-content-between'>
				<ScrollableSection ref={this._scrollableSectionRef}>
					<ul className='d-flex'>
						{itemsIList.map(({id, title}, i) => (
							<TabItem
								active={
									selectedTabIndex === i && !showAddProperty
								}
								disabled={showAddProperty}
								id={id}
								key={title}
								onDelete={onDelete}
								onSelect={onSelect}
								title={title}
							/>
						))}
					</ul>
				</ScrollableSection>

				<Button
					active={showAddProperty}
					borderless
					className='add-tab'
					display='secondary'
					monospaced
					onClick={onAdd}
					size='sm'
				>
					<Icon symbol='plus' />
				</Button>
			</div>
		);
	}
}
