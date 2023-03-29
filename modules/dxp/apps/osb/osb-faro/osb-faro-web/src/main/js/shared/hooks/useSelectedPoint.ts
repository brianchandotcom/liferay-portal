import {isNull} from 'lodash';
import {useState} from 'react';

export default function useSelectedPoint(): {
	hasSelectedPoint: boolean;
	onPointSelect: (any) => void;
	selectedPoint: number;
} {
	const [selectedPoint, onPointSelect] = useState<number>();

	return {
		hasSelectedPoint: !isNull(selectedPoint) && isFinite(selectedPoint),
		onPointSelect,
		selectedPoint
	};
}
