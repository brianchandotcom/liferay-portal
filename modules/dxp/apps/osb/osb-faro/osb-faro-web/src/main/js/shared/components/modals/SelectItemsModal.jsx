import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import EntityList from 'shared/components/EntityList';
import ListGroup from 'shared/components/list-group';
import ListView from 'shared/components/ListView';
import Loading, {Align} from 'shared/components/Loading';
import Modal from 'shared/components/modal';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import Promise from 'metal-promise';
import React from 'react';
import SearchableModal from './SearchableModal';
import {noop} from 'lodash';
import {PropTypes} from 'prop-types';
import {Set} from 'immutable';

export class ItemComponent extends React.Component {
	render() {
		const {
			className,
			item: {name}
		} = this.props;
		return (
			<ListGroup.ItemField className={className} expand>
				{name}
			</ListGroup.ItemField>
		);
	}
}

export default class SelectItemsModal extends React.Component {
	static defaultProps = {
		expandDataFn: noop,
		onClose: noop,
		onSubmit: noop,
		requireSelection: true,
		selectedItems: [],
		selectMultiple: true,
		submitMessage: Liferay.Language.get('select'),
		title: Liferay.Language.get('select-fields')
	};

	static propTypes = {
		dataSourceFn: PropTypes.func.isRequired,
		disabledSelectedDataSourceFn: PropTypes.func,
		entityType: PropTypes.number,
		expandDataFn: PropTypes.func,
		groupId: PropTypes.string.isRequired,
		onClose: PropTypes.func,
		onSubmit: PropTypes.func,
		requireSelection: PropTypes.bool,
		selectedItems: PropTypes.array,
		selectMultiple: PropTypes.bool,
		submitMessage: PropTypes.string,
		title: PropTypes.string
	};

	state = {
		disabledItemsISet: new Set(),
		items: [],
		selectedItemsISet: new Set(),
		submitting: false
	};

	constructor(props) {
		super(props);

		const {selectedItems} = this.props;

		if (selectedItems && selectedItems.length) {
			this.state = {
				...this.state,
				selectedItemsISet: new Set(selectedItems.map(({id}) => id))
			};
		}
	}

	componentDidMount() {
		const {disabledSelectedDataSourceFn} = this.props;

		if (disabledSelectedDataSourceFn) {
			disabledSelectedDataSourceFn()
				.then(({items}) => {
					const ids = items.map(({id}) => id);

					this.setState = {
						disabledItemsISet: new Set(ids),
						selectedItemsISet: new Set(ids)
					};
				})
				.catch(noop);
		}
	}

	getSelectedItems() {
		const {disabledItemsISet, items, selectedItemsISet} = this.state;

		return items.filter(
			({id}) => selectedItemsISet.has(id) && !disabledItemsISet.has(id)
		);
	}

	@autobind
	handleChange(items) {
		const selectedItems = this.getSelectedItems().filter(
			selectedItem => !items.find(item => item.id === selectedItem.id)
		);

		this.setState({
			items: [...selectedItems, ...items]
		});
	}

	@autobind
	handleSelectItemsChange(newVal) {
		this.setState({
			selectedItemsISet: newVal
		});
	}

	@autobind
	handleSelectTreeItemsChange(newVal) {
		this.setState({
			selectedItemsISet: new Set(newVal)
		});
	}

	@autobind
	handleSubmit() {
		const {onClose, onSubmit} = this.props;

		this.setState({
			submitting: true
		});

		const submitResult = onSubmit(this.getSelectedItems());

		if (submitResult instanceof Promise) {
			submitResult
				.then(() => {
					this.setState({
						submitting: false
					});

					onClose();
				})
				.catch(err => {
					if (!err.IS_CANCELLATION_ERROR) {
						this.setState({
							submitting: false
						});
					}
				});
		} else {
			onClose();
		}
	}

	render() {
		const {
			props: {
				dataSourceFn,
				entityType,
				fitContent,
				groupId,
				onClose,
				requireSelection,
				selectMultiple,
				submitMessage,
				title,
				...otherProps
			},
			state: {disabledItemsISet, items, selectedItemsISet, submitting}
		} = this;

		return (
			<SearchableModal
				{...omitDefinedProps(otherProps, SelectItemsModal.propTypes)}
				dataSourceFn={dataSourceFn}
				fitContent={fitContent}
				footer={
					<Modal.Footer>
						<ClayButton
							className='button-root'
							displayType='secondary'
							onClick={onClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							className='button-root'
							disabled={
								requireSelection &&
								!this.getSelectedItems().length
							}
							displayType='primary'
							onClick={this.handleSubmit}
						>
							{submitting && <Loading align={Align.Left} />}

							{submitMessage}
						</ClayButton>
					</Modal.Footer>
				}
				items={items}
				onChange={this.handleChange}
				onClose={onClose}
				title={title}
			>
				{entityType ? (
					<EntityList
						disabledItemsISet={disabledItemsISet}
						groupId={groupId}
						items={items}
						onSelectItemsChange={this.handleSelectItemsChange}
						selectedItemsISet={selectedItemsISet}
						selectMultiple={selectMultiple}
					/>
				) : (
					<ListView
						disabledItemsISet={disabledItemsISet}
						itemRenderer={ItemComponent}
						items={items}
						onSelectItemsChange={this.handleSelectItemsChange}
						selectedItemsISet={selectedItemsISet}
						selectMultiple={selectMultiple}
					/>
				)}
			</SearchableModal>
		);
	}
}
