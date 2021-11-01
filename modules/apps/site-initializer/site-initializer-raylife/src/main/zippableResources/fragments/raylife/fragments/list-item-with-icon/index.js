const icon = fragmentElement.querySelector("#plus-icon");
const textDiv = fragmentElement.querySelector("#collapseExample");

icon.onclick = function(){
	let flag = textDiv.classList.contains("collapse");
	if(flag){
			textDiv.classList.remove("collapse");
	}
	else{
		textDiv.classList.add("collapse");
	}
}