import CardTabs, {CardTabSizes} from 'shared/components/CardTabs';
import React from 'react';

interface IHeaderProps {
	activeTabId: string;
	tabs: {
		onClick: () => void;
		tabId: string;
		title: string;
	}[];
	title: string;
}

const Header: React.FC<IHeaderProps> = ({activeTabId, tabs, title}) => (
	<div className='event-analysis-dropdown-header'>
		<div className='event-analysis-dropdown-header-title'>{title}</div>

		<CardTabs
			activeTabId={activeTabId}
			className='event-type-selector'
			size={CardTabSizes.Small}
			tabs={tabs}
		/>
	</div>
);

export default Header;
