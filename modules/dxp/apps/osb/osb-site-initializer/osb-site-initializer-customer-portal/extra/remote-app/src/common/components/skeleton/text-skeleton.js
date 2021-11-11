import classNames from 'classnames';

const TextSkeleton = ({align, count, height, width, ...props}) => {
	const line = (factor = 0) => (
		<div
			className={classNames(
				'rounded skeleton',
				{
					'ml-auto': align === 'right',
					'mr-auto': align === 'left',
					'mx-auto': align === 'center',
				},
				{
					'mt-3': factor > 0,
				}
			)}
			style={{height: `${height}px`, width: `${width - factor * 100}px`}}
		></div>
	);
	const linesElement = [line()];

	if (count > 1) {
		for (let counter = 1; counter < count; counter++) {
			linesElement.push(line(counter));
		}
	}

	return <div {...props}>{linesElement}</div>;
};

export default TextSkeleton;
