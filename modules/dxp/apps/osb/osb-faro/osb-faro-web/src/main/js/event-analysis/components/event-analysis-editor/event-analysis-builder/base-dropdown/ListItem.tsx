import ClayButton from '@clayui/button';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import InfoCardPopover from '../InfoCardPopover';
import Overlay from 'shared/components/Overlay';
import React, {useRef} from 'react';
import {Attribute, Event} from 'event-analysis/utils/types';
import {DATA_TYPE_ICONS_MAP, isAttribute} from 'event-analysis/utils/utils';

interface IListItemProps {
	active?: boolean;
	disabled?: boolean;
	editable?: boolean;
	item: Attribute | Event;
	onClick: () => void;
	onEditClick: () => void;
	onOptionsClick: (item: any) => void;
}

const ListItem: React.FC<IListItemProps> = ({
	active,
	disabled,
	editable = true,
	item,
	onClick,
	onEditClick,
	onOptionsClick
}) => {
	const _overlayRef = useRef<any>();

	const {description, displayName, id, name} = item;

	return (
		<Overlay
			alignment='leftCenter'
			hideDelay={200}
			ref={_overlayRef}
			showDelay={200}
			usePortal={false}
		>
			<ClayDropdown.Item
				className={getCN('d-flex justify-content-between', {
					active,
					disabled
				})}
				key={id}
			>
				<ClayButton
					block
					className='button-root dropdown-item-primary-button'
					disabled={disabled}
					displayType='unstyled'
					onClick={() => {
						if (_overlayRef && _overlayRef.current) {
							_overlayRef.current.hideOverlay();
						}

						onClick();
					}}
				>
					{isAttribute(item as Attribute) && (
						<div className='sticker'>
							<ClayIcon
								className='icon-root'
								symbol={
									DATA_TYPE_ICONS_MAP[
										(item as Attribute).dataType
									]
								}
							/>
						</div>
					)}

					{displayName || name}
				</ClayButton>

				{!!onOptionsClick && (
					<ClayButton
						aria-label={Liferay.Language.get('control-panel')}
						borderless
						className='button-root options-button'
						disabled={disabled}
						displayType='secondary'
						onClick={() => {
							if (_overlayRef && _overlayRef.current) {
								_overlayRef.current.hideOverlay();
							}

							onOptionsClick(item);
						}}
						size='sm'
					>
						<ClayIcon
							className='icon-root'
							symbol='control-panel'
						/>
					</ClayButton>
				)}
			</ClayDropdown.Item>

			<InfoCardPopover
				dataType={
					isAttribute(item as Attribute)
						? (item as Attribute).dataType
						: null
				}
				description={description}
				name={displayName || name}
				onEditClick={
					editable
						? () => {
								if (_overlayRef && _overlayRef.current) {
									_overlayRef.current.hideOverlay();
								}

								onEditClick();
						  }
						: null
				}
			/>
		</Overlay>
	);
};

export default ListItem;
