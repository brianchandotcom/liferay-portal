import * as API from 'shared/api';
import ClayTable from '@clayui/table';
import getCN from 'classnames';
import Modal from 'shared/components/modal';
import React, {useState} from 'react';
import {noop} from 'lodash';
import {sub} from 'shared/util/lang';
import {useEffect} from 'react';

const ROW_DELTA = 10;

interface ICSVPreviewModalProps extends React.HTMLAttributes<HTMLElement> {
	fieldName?: string;
	fileVersionId?: string | number;
	groupId: string;
	id: string;
	name: string;
	onClose?: () => void;
}

const CSVPreviewModal: React.FC<ICSVPreviewModalProps> = ({
	className,
	fieldName,
	fileVersionId,
	groupId,
	id,
	name,
	onClose = noop
}) => {
	const [data, setData] = useState([]);

	useEffect(() => {
		const fetchData = () => {
			API.dataSource
				.fetchFieldValues({
					count: ROW_DELTA,
					fieldName,
					fileVersionId,
					groupId,
					id
				})
				.then(setData)
				.catch(noop);
		};

		fetchData();
	}, [fieldName, fileVersionId, groupId, id]);

	return (
		<Modal className={getCN('csv-preview-modal-root', className)} size='lg'>
			<Modal.Header
				onClose={onClose}
				title={sub(Liferay.Language.get('data-preview-x'), [name])}
			/>

			<Modal.Body>
				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							{data.map(({name}, i) => (
								<ClayTable.Cell expanded headingCell key={i}>
									{name}
								</ClayTable.Cell>
							))}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{!!data.length &&
							data[0].values.map((val, row) => (
								<ClayTable.Row key={row}>
									{data.map((val, column) => (
										<ClayTable.Cell
											key={`${column}-${row}`}
										>
											{data[column].values[row]}
										</ClayTable.Cell>
									))}
								</ClayTable.Row>
							))}
					</ClayTable.Body>
				</ClayTable>
			</Modal.Body>
		</Modal>
	);
};

export default CSVPreviewModal;
