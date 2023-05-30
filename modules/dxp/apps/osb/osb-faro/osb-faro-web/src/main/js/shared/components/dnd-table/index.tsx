import ClayTable from '@clayui/table';
import React from 'react';
import Row, {Column} from './Row';
import TextTruncate from 'shared/components/TextTruncate';
import {curry, flow} from 'lodash';
import {DndProvider} from 'react-dnd';
import {getRowIdentifierValue} from 'shared/components/table';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {moveItem} from 'shared/util/array';

interface IDndTableProps {
	columns: Column[];
	items: any[];
	onItemsChange: (items: any[]) => void;
	rowIdentifier?: string | string[];
}

const DndTable: React.FC<IDndTableProps> = ({
	columns,
	items,
	onItemsChange,
	rowIdentifier = 'id'
}) => {
	const handleMove: (from: number, to: number) => void = flow([
		curry(moveItem)(items),
		onItemsChange
	]);

	return (
		<div className=' dnd-table-root'>
			<DndProvider backend={HTML5Backend}>
				<ClayTable className='dnd-table-root'>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell />

							{columns.map(({label}, i) => (
								<ClayTable.Cell headingCell key={i}>
									<TextTruncate title={label}>
										{label}
									</TextTruncate>
								</ClayTable.Cell>
							))}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{items.map((item, i) => (
							<Row
								columns={columns}
								data={item}
								draggable={item.draggable}
								index={i}
								key={getRowIdentifierValue(item, rowIdentifier)}
								onMove={handleMove}
							/>
						))}
					</ClayTable.Body>
				</ClayTable>
			</DndProvider>
		</div>
	);
};

export default DndTable;
