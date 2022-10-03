let requestType = document.querySelector('[name="requestType"]');
const serviceForm = document.getElementsByClassName(".service-form");
const grantForm = document.getElementsByClassName(".grant-form");
document.addEventListener('click', handleDocumentClick);



function updateValue(requestType){
			if (requestType.value == "grant" && grantForm[0].classList.contains("d-none")){
				grantForm[0].classList.toggle('d-none');
				toggleGrantRequired(grantForm[0])
				
				if(!serviceForm[0].classList.contains("d-none")){
					serviceForm[0].classList.toggle('d-none');
					toggleServiceRequired(serviceForm[0])
				}
	}
		if (requestType.value == "service" && serviceForm[0].classList.contains("d-none")) {
			serviceForm[0].classList.toggle('d-none');
			toggleServiceRequired(serviceForm[0])
			
				if(!grantForm[0].classList.contains("d-none")){
					grantForm[0].classList.toggle('d-none');
					toggleGrantRequired(grantForm[0])
				}
		}
}
function toggleServiceRequired(service){
	if(service.querySelector('[name="managerEmailAddress"]').required){
		service.querySelector('[name="managerEmailAddress"]').required = false;
		service.querySelector('[name="totalHoursRequested"]').required = false;
		service.querySelector('[name="startDate"]').required = false;
		service.querySelector('[name="endDate"]').required = false;
	}else{
		service.querySelector('[name="managerEmailAddress"]').required = true;
		service.querySelector('[name="totalHoursRequested"]').required = true;
		service.querySelector('[name="startDate"]').required = true;
		service.querySelector('[name="endDate"]').required = true;
	}
}

function toggleGrantRequired(grant){
	if(grant.querySelector('[name="grantAmount"]').required){
    grant.querySelector('[name="grantAmount"]').required = false;
}else{
    grant.querySelector('[name="grantAmount"]').required = true;
}

}


function handleDocumentClick() {
	updateValue(requestType)
}