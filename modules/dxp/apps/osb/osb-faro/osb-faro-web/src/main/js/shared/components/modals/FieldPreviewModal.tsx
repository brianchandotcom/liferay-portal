import getCN from 'classnames';
import ListGroup from 'shared/components/list-group';
import Modal from 'shared/components/modal';
import Promise from 'metal-promise';
import React, {useEffect, useState} from 'react';
import {get, noop} from 'lodash';
import {sub} from 'shared/util/lang';

interface IFieldPreviewModalProps extends React.HTMLAttributes<HTMLDivElement> {
	dataSourceFn: () => Promise<any>;
	fieldName: string;
	onClose: () => void;
	sourceName: string;
}

const FieldPreviewModal: React.FC<IFieldPreviewModalProps> = ({
	className,
	dataSourceFn,
	fieldName,
	onClose = noop,
	sourceName,
	...otherProps
}) => {
	const [fieldData, setFieldData] = useState([]);

	useEffect(() => {
		getFieldData();
	}, []);

	const getFieldData = () =>
		dataSourceFn().then(fieldData => {
			setFieldData(get(fieldData, [0, 'values'], []));
		});

	return (
		<Modal
			{...otherProps}
			className={getCN('field-preview-modal-root', className)}
			size='lg'
		>
			<Modal.Header
				onClose={onClose}
				title={sub(Liferay.Language.get('field-preview-x'), [
					sourceName
				])}
			/>

			<ListGroup>
				<ListGroup.Item flex header>
					<ListGroup.ItemField>{fieldName}</ListGroup.ItemField>
				</ListGroup.Item>

				{fieldData.map((item, index) => (
					<ListGroup.Item key={index}>
						<ListGroup.ItemField>{item}</ListGroup.ItemField>
					</ListGroup.Item>
				))}
			</ListGroup>
		</Modal>
	);
};

export default FieldPreviewModal;
