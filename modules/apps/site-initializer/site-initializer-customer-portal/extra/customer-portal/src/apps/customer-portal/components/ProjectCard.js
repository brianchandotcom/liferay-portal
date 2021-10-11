import ClayCard from '@clayui/card';
import BaseButton from '~/shared/components/BaseButton';
import StatusTag from './StatusTag';

const ProjectCard = ({ title, status, endDate, state, onClick, small }) => {

  return (
    <ClayCard className={`project-card${small ? "-sm" : ""} m-0`} onClick={() => onClick}>
      <ClayCard.Body className={`d-flex flex-${small ? "row" : "column"} h-100 justify-content-between`}>
        <ClayCard.Description className="text-neutral-3" displayType="title" tag={`${small ? "h4" : "h3"}`}>
          {title}
        </ClayCard.Description>
        <div className={`${small ? "" : "align-items-end "}d-flex justify-content-between`}>
          <ClayCard.Description truncate={false} displayType="text" tag="div" title={null}>
            <StatusTag currentStatus={status} />
            <div className={`text-paragraph-sm text-neutral-5${small ? " sm-mb" : " my-1"}`}>
              Ends on <span className="text-paragraph font-weight-bold">{endDate}</span>
            </div>
            <div className="text-paragraph-sm text-neutral-5">
              {state}
            </div>
          </ClayCard.Description>
          {!small && <BaseButton className="p-0 text-brand-primary" appendIcon="angle-right" borderless>
            See details
          </BaseButton>}
        </div>
      </ClayCard.Body>
    </ClayCard>
  );
};

export default ProjectCard;