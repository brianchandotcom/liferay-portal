const SquareSkeleton = ({height, width}) => {
	return (
		<div
			className="skeleton"
			style={{height: `${height}px`, width: `${width}px`}}
		></div>
	);
};

export default SquareSkeleton;
