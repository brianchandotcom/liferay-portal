import ClayLink from '@clayui/link';
import Icon from './Icon';
import React, { useEffect, useCallback } from "react";
import SwaggerUI from "swagger-ui-react";
import { createRoot } from 'react-dom/client';

const CustomSwaggerUI = ({ learnResources = [], ...props }) => {
  const addTooltips = useCallback(() => {

    if (!Array.isArray(learnResources)) return;

    learnResources.forEach(({ key, learnMessageDetails }) => {
      const rows = document.querySelectorAll(`[data-param-name='${key}']`);
      
      rows.forEach((row) => {
        if (row && !row.querySelector(".tooltip-container")) {
          const inputContainer = row.querySelector("td.parameters-col_description");
          
          if (inputContainer) {
            const tooltipContainer = document.createElement("div");
            tooltipContainer.className = "tooltip-container";
            
            tooltipContainer.innerHTML = `
              <span class="tooltip-icon">
                <div id="tooltip-icon-wrapper-${key}"></div>
              </span>
              <span class="tooltip-text">
                <div id="tooltip-content-${key}"></div>
              </span>
            `;
            
            inputContainer.appendChild(tooltipContainer);
            
            const iconWrapper = tooltipContainer.querySelector(`#tooltip-icon-wrapper-${key}`);
            if (iconWrapper) {
              const iconRoot = createRoot(iconWrapper);
              iconRoot.render(<Icon symbol="question-circle" />);
            }
            
            const tooltipContent = tooltipContainer.querySelector(`#tooltip-content-${key}`);
            if (tooltipContent && learnMessageDetails?.[0]) {
              const messageRoot = createRoot(tooltipContent);
              messageRoot.render(
                <ClayLink
                  href={learnMessageDetails[0].url}
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  {learnMessageDetails[0].message}
                </ClayLink>
              );
            }
          }
        }
      });
    });
  }, [learnResources]);

  useEffect(() => {
    const addTooltipsWithDelay = () => {
      Promise.resolve().then(addTooltips);
    };

    addTooltipsWithDelay();

    const swaggerContainer = document.getElementById("swagger-main");
    const observer = new MutationObserver(addTooltipsWithDelay);

    if (swaggerContainer) {
      observer.observe(swaggerContainer, { 
        childList: true, 
        subtree: true 
      });
    }

    return () => {
      observer.disconnect();
    };
  }, [addTooltips]);

  const { learnResources: _, ...swaggerProps } = props;

  return (
    <div id="swagger-main">
      <SwaggerUI {...swaggerProps} />
    </div>
  );
};

export default CustomSwaggerUI;