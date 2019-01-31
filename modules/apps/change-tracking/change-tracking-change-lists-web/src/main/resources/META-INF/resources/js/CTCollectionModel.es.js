
class CTCollectionModel {

	constructor(
		id,
		companyId,
		name,
		additionCount,
		deletionCount,
		modificationCount,  
		statusByUserName,
		statusDate) {

		this.id = id;	
		this.companyId = companyId;
		this.name = name;
		this.additionCount = additionCount;
		this.deletionCount = deletionCount;
		this.modificationCount = modificationCount;
		this.statusByUserName = statusByUserName;
		this.statusDate = statusDate;
	}

	static build(object) {
		return new CTCollectionModel(
			object.id,
			object.companyId,
			object.name,
			object.additionCount,
			object.deletionCount,
			object.modificationCount,  
			object.statusByUserName,
			object.statusDate
		)
	}
}

export {CTCollectionModel};
export default CTCollectionModel;