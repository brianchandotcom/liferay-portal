import getCN from 'classnames';
import getSVG from 'shared/util/svg';
import React, {useEffect, useState} from 'react';
interface IThumbsProps extends React.HTMLAttributes<HTMLElement> {
	items: Item[];
	onSelectThumb: (params: Item) => void;
}

type Item = {
	selected: boolean;
	svg: string;
	text: string;
	value: string;
};

const Thumbs: React.FC<IThumbsProps> = ({
	items: initialItems,
	onSelectThumb
}) => {
	const [items, setitems] = useState<Item[]>(initialItems);

	useEffect(() => {
		const selectedItem = items.find(({selected}) => selected);

		onSelectThumb(selectedItem);
	}, [items]);

	const selectThumb = (id: number) => {
		const updatedItems = items.map((item: Item, index: number) => ({
			...item,
			selected: id === index
		}));

		setitems(updatedItems);
	};

	return (
		<div className='analytics-add-report-thumbs'>
			{items.map(({selected, svg, text}, index) => {
				const {id, viewBox} = getSVG(svg);

				return (
					<button
						className={getCN({selected})}
						data-tooltip
						key={index}
						onClick={() => selectThumb(index)}
						onKeyPress={() => selectThumb(index)}
						title={text}
					>
						<svg className={svg} viewBox={viewBox}>
							<use
								xlinkHref={`/o/osb-faro-web/dist/sprite.svg#${id}`}
							/>
						</svg>
					</button>
				);
			})}
		</div>
	);
};

export default Thumbs;
