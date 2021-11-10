const RoundedSkeleton = ({ height, width }) => {
    return (<div className="rounded-sm skeleton" style={{ height: `${height}px`, width: `${width}px` }}></div>);
}

export default RoundedSkeleton;