const compareArrays = (array1, array2) => {
	if (array1 === array2) return true;

	if (!array1 || !array2 || array1.length != array2.length) return false;

	return array1.reduce(
		(acc, cur, index) => !!acc && cur === array2[index],
		true
	);
};

export {compareArrays};
