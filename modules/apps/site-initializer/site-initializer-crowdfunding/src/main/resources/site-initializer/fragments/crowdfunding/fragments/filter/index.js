var input = document.querySelector(".crowse-container .crowse-input.facet");
var dropdown = document.querySelector(".crowse-container .dropdown");

input.addEventListener("click",function(){
	dropdown.classList.toggle("d-block");
});

var count = 0;
document.querySelectorAll(".crowse-container .dropdown .portlet-body input").forEach(function(e){
   if(e.checked){
       count++;
	}
});

if(count){
	var numberSpan = document.querySelector(".crowse-input.facet .circle");
  numberSpan.classList.add("d-flex");
	numberSpan.innerText = count;
}