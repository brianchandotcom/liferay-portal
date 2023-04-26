import ClayButton from '@clayui/button';
import getCN from 'classnames';
import React from 'react';

export enum ButtonDisplayMode {
	SPACED_BUTTONS = 'mdn-button-tab'
}

export enum CardTabSizes {
	Small = 'small'
}

interface ICardTabsProps {
	activeTabId: number | string;
	buttonsDisplayMode?: ButtonDisplayMode;
	className?: string;
	onChange?: (tabId: string) => void;
	size?: CardTabSizes;
	tabs: Array<any>;
}

const CardTabs: React.FC<ICardTabsProps> = ({
	activeTabId,
	buttonsDisplayMode,
	className,
	onChange,
	size,
	tabs
}) => {
	const handleEmitOnChange = (onClick, tabId): void => {
		onClick && onClick();
		onChange && onChange(tabId);
	};

	return (
		<ul
			className={getCN(
				'card-tabs-root',
				className,
				buttonsDisplayMode,
				size
			)}
		>
			{tabs.map(({onClick, secondaryInfo, tabId, title}) => (
				<li
					className={getCN('card-tab', {
						active: activeTabId === tabId
					})}
					data-testid={tabId}
					key={tabId}
				>
					<ClayButton
						className='button-root'
						displayType='unstyled'
						onClick={() => handleEmitOnChange(onClick, tabId)}
					>
						<span className='title'>{title}</span>

						<div>{secondaryInfo}</div>
					</ClayButton>
				</li>
			))}
		</ul>
	);
};

export default CardTabs;
