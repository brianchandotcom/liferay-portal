<div class="lfr-form-content" id="portlet-configuration">
  <div class="sheet sheet-lg" style="visibility: hidden" id="sheet-portlet">
    <div class="panel-group" aria-multiselectable="true" role="tablist">
      <div id="$PORTLET_ID" data-instance="$INSTANCE">
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">

    window.onload = function() {

    const PORTLET_NAME = '$PORTLET_ID';
    const INSTANCE = '$INSTANCE';
    const BASE_URL_CONFIGURATION = '$URL_CONFIGURATION';
    let configuration = JSON.parse('$OBJECT_CONFIGURATION');
    let configurationValues = JSON.parse('$OBJECT_VALUES');

    console.log(configurationValues);

    let wrapperAll = document.getElementById(PORTLET_NAME);
    let wrapperPanel = document.getElementById('portlet-configuration');

    function wrapperSimpleField(PORTLET_NAME, key, { label, options, suffix, prefix, inlineField,
     type = 'text', title, helpMessage, inlineLabel = '', required = false, placeholder = '', multiple = false }) {

     let requiredDivContent =
        `<span class="reference-mark text-warning" id="xdtv____">
          <svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="img" title="" viewBox="0 0 512 512">
            <title>asterisk</title>
            <path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
          </svg>
        </span>
        <span class="hide-accessible">Required</span>
        `;

     function helpMessageDivContent(helpMessage) {
      return `<span class="taglib-icon-help lfr-portal-tooltip" title="${helpMessage}">
         <span id="nayw____">
          <svg class="lexicon-icon lexicon-icon-question-circle-full" focusable="false" role="img" title="" viewBox="0 0 512 512">
           <title>question-circle-full</title>
           <path class="lexicon-icon-outline" d="M256 0c-141.37 0-256 114.6-256 256 0 141.37 114.629 256 256 256s256-114.63 256-256c0-141.4-114.63-256-256-256zM269.605 360.769c-4.974 4.827-10.913 7.226-17.876 7.226s-12.873-2.428-17.73-7.226c-4.857-4.827-7.285-10.708-7.285-17.613 0-6.933 2.428-12.844 7.285-17.788 4.857-4.915 10.767-7.402 17.73-7.402s12.932 2.457 17.876 7.402c4.945 4.945 7.431 10.854 7.431 17.788 0 6.905-2.457 12.786-7.431 17.613zM321.038 232.506c-5.705 8.923-13.283 16.735-22.791 23.464l-12.99 9.128c-5.5 3.979-9.714 8.455-12.668 13.37-2.955 4.945-4.447 10.649-4.447 17.145v1.901h-34.202c-0.439-2.106-0.731-4.184-0.936-6.291s-0.321-4.301-0.321-6.612c0-8.397 1.901-16.413 5.705-24.079s10.24-14.834 19.309-21.563l15.185-11.322c9.070-6.7 13.605-15.009 13.605-24.869 0-3.57-0.644-7.080-1.901-10.533s-3.219-6.495-5.851-9.128c-2.633-2.633-5.969-4.71-9.977-6.291s-8.66-2.369-13.927-2.369c-5.705 0-10.561 1.054-14.571 3.16s-7.343 4.769-9.977 8.017c-2.633 3.247-4.594 7.022-5.851 11.322s-1.901 8.66-1.901 13.049c0 4.213 0.41 7.548 1.258 10.065l-39.877-1.58c-0.644-2.311-1.054-4.652-1.258-7.080-0.205-2.399-0.321-4.769-0.321-7.080 0-8.397 1.58-16.619 4.74-24.693s7.812-15.214 13.927-21.416c6.114-6.173 13.663-11.176 22.645-14.951s19.368-5.676 31.188-5.676c12.229 0 22.996 1.785 32.3 5.355 9.274 3.57 17.087 8.25 23.435 14.014 6.319 5.764 11.089 12.434 14.248 19.982s4.74 15.331 4.74 23.289c0.058 12.581-2.809 23.347-8.514 32.27z"></path>
          </svg>
         </span>
       <span class="taglib-text hide-accessible">${helpMessage}</span>
       </span>`;
     }

      if(["text", "date", "textarea", "time", "number", "password", "color", "url", "email", "range"].includes(type)) {

      return `
             <fieldset aria-labelledby="Title" class="" role="group">
               <div aria-labelledby="Header" class="in" id="Content" role="presentation">
                 <div class="panel-body" style="margin-bottom: 0">
                   <div class="form-group input-text-wrapper ${inlineField ? 'form-inline form-group-inline' : ''}">
                   <label class="control-label" for="${PORTLET_NAME}${key}"> ${label}
                   ${ required ? requiredDivContent : '' }
                   ${ helpMessage ? helpMessageDivContent(helpMessage) : '' }
                   ${
                   suffix || prefix && type !== "textarea" ?
                   `
                   <div class="input-group">
                    <span class="input-group-addon">${prefix || ''}</span>
                    <input class="field form-control" id="${PORTLET_NAME}${key}" name="${PORTLET_NAME}${key}"
                      placeholder="${placeholder}" title="${title || key}" type="${type}" value="${configurationValues[key] ? configurationValues[key][0] : ''}"
                    />
                    <span class="input-group-addon">${suffix || ''}</span>
                    </div>
                    </label>
                   ` :
                   type === "textarea" ?
                   `</label>
                    <textarea class="field form-control" id="${PORTLET_NAME}${key}"
                    name="${PORTLET_NAME}${key}" placeholder="${placeholder}">${configurationValues[key] ? configurationValues[key][0] : ''}</textarea>
                   `
                   :
                   `</label>
                   <input class="field form-control" id="${PORTLET_NAME}${key}" name="${PORTLET_NAME}${key}" placeholder="${placeholder}" title="${title || key}"
                    type="${type}" value=${configurationValues[key] ? configurationValues[key][0] : ''} />
                    </div>
                   `
                   }

                 </div>
               </div>
             </fieldset>
        `;
      } else if(["radio", "checkbox"].includes(type)) {
        return `
        <fieldset aria-labelledby="Title" class=" " role="group">
          <div aria-labelledby="Header" class="in " id="Content" role="presentation">
            <div class="panel-body" style="margin-bottom: 0">
              <div class="form-group input-checkbox-wrapper ${inlineField ? 'form-inline form-group-inline' : ''}">
                <label class="control-label" for="${PORTLET_NAME}${key}"> ${label}
                  ${ required ? requiredDivContent : '' }
                  ${ helpMessage ? helpMessageDivContent(helpMessage) : '' }
                </label>
              </div>
              ${options.map(option => `
               <div class="${type === 'checkbox'
               ? `form-group input-checkbox-wrapper ${inlineField ? 'form-group-inline form-inline' : ''}`
               : 'radio'}">
                <label class="checkbox-inline" for="${PORTLET_NAME}${option.value}">
                  <input class="field" ${configurationValues[key] ? configurationValues[key].includes(option.value) ? 'checked' : '' : ''} id="${PORTLET_NAME}${option.value}" name="${PORTLET_NAME}${key}" onclick="" type="${type}" value="${option.value}">
                    ${option.label}
                </label>
               </div>`).join('')}
            </div>
          </div>
        </fieldset>`;
      } else if("select" === type) {
         return `
         <fieldset aria-labelledby="Title" class=" " role="group">
           <div aria-labelledby="Header" class="in " id="Content" role="presentation">
             <div class="panel-body" style="margin-bottom: 0">
               <div class="form-group input-select-wrapper">
                 <label class="control-label" for="${PORTLET_NAME}${key}"> ${label}
                  ${ required ? requiredDivContent : '' }
                  ${ helpMessage ? helpMessageDivContent(helpMessage) : '' }
                 </label>
                 <select class="form-control" id="${PORTLET_NAME}${key}" ${multiple ? 'multiple' : ''} name="${PORTLET_NAME}${key}">
                  ${options.map(option =>
                  `<option ${configurationValues[key] ? configurationValues[key].includes(option.value) ? 'selected' : '' : ''} value="${option.value}" type="${type}" title="${title || key}" showemptyoption="true" name="${key}" id="${key}">
                    ${option.label}
                   </option>`).join('')}
                 </select>
               </div>
             </div>
           </div>
         </fieldset>`;
      } else if("toggle-switch" === type) {
        return `
          <fieldset aria-labelledby="Title" class="" role="group">
            <div aria-labelledby="Header" class="in " id="Content" role="presentation">
              <div class="panel-body" style="margin-bottom: 0">
                <div class="form-group form-inline input-checkbox-wrapper">
                  <label for="${PORTLET_NAME}${key}">
                  <input ${configurationValues[key] ? configurationValues[key][0] === 'on' ? 'checked' : '' : '' } class="field toggle-switch" id="${PORTLET_NAME}${key}" name="${PORTLET_NAME}${key}" onclick="" type="checkbox">
                  <span class="toggle-switch-label">${label}</span>
                  <span aria-hidden="true" class="toggle-switch-bar">
                  <span class="toggle-switch-handle" data-label-off="No" data-label-on="Yes"></span></span>
                  <span class="toggle-switch-text toggle-switch-text-right">
                  <span class="taglib-icon-help lfr-portal-tooltip" title="toggle-switch">
                  <span id="wmge____">
                  <svg class="lexicon-icon lexicon-icon-question-circle-full" focusable="false" role="img" title="" viewBox="0 0 512 512">
                   <title>question-circle-full</title>
                   <path class="lexicon-icon-outline" d="M256 0c-141.37 0-256 114.6-256 256 0 141.37 114.629 256 256 256s256-114.63 256-256c0-141.4-114.63-256-256-256zM269.605 360.769c-4.974 4.827-10.913 7.226-17.876 7.226s-12.873-2.428-17.73-7.226c-4.857-4.827-7.285-10.708-7.285-17.613 0-6.933 2.428-12.844 7.285-17.788 4.857-4.915 10.767-7.402 17.73-7.402s12.932 2.457 17.876 7.402c4.945 4.945 7.431 10.854 7.431 17.788 0 6.905-2.457 12.786-7.431 17.613zM321.038 232.506c-5.705 8.923-13.283 16.735-22.791 23.464l-12.99 9.128c-5.5 3.979-9.714 8.455-12.668 13.37-2.955 4.945-4.447 10.649-4.447 17.145v1.901h-34.202c-0.439-2.106-0.731-4.184-0.936-6.291s-0.321-4.301-0.321-6.612c0-8.397 1.901-16.413 5.705-24.079s10.24-14.834 19.309-21.563l15.185-11.322c9.070-6.7 13.605-15.009 13.605-24.869 0-3.57-0.644-7.080-1.901-10.533s-3.219-6.495-5.851-9.128c-2.633-2.633-5.969-4.71-9.977-6.291s-8.66-2.369-13.927-2.369c-5.705 0-10.561 1.054-14.571 3.16s-7.343 4.769-9.977 8.017c-2.633 3.247-4.594 7.022-5.851 11.322s-1.901 8.66-1.901 13.049c0 4.213 0.41 7.548 1.258 10.065l-39.877-1.58c-0.644-2.311-1.054-4.652-1.258-7.080-0.205-2.399-0.321-4.769-0.321-7.080 0-8.397 1.58-16.619 4.74-24.693s7.812-15.214 13.927-21.416c6.114-6.173 13.663-11.176 22.645-14.951s19.368-5.676 31.188-5.676c12.229 0 22.996 1.785 32.3 5.355 9.274 3.57 17.087 8.25 23.435 14.014 6.319 5.764 11.089 12.434 14.248 19.982s4.74 15.331 4.74 23.289c0.058 12.581-2.809 23.347-8.514 32.27z"></path>
                  </svg>
                   </span>
                   <span class="taglib-text hide-accessible">${helpMessage || label}</span>
                   </span>
                  </span>
                </label>
              </div>
            </div>
          </div>
        </fieldset>`;
      }
      else {
        return `<div class='form-group'><div class='control-label'>Input type is not a standard HTML5 one. type: ${type} | key: ${key}</div></div>`;
      }
    }

      // Mount all FORM inputs
      // Object.entries(configuration).map(([key, values]) => {
      //  wrapperAll.innerHTML += (wrapperSimpleField(PORTLET_NAME, key, values));
      // });
      
      document.getElementById("sheet-portlet").style.visibility = 'visible';

      // Apend a footer with the action button to the FORM
      wrapperPanel.innerHTML +=
      `
      <div class="button-holder dialog-footer">
        <button class="btn btn-primary btn-default" id="form-button-submit" type="button">
         <span class="lfr-btn-label">Save</span>
        </button>
      </div>
      `;

      // Bind a submit action for the FORM
      document.getElementById("form-button-submit").onclick = function () {
        document.getElementById(`${PORTLET_NAME}fm`).action = BASE_URL_CONFIGURATION;
        document.getElementById(`${PORTLET_NAME}fm`).submit();
      }
    };

</script>
