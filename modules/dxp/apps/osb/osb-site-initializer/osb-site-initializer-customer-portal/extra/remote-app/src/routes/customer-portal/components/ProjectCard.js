import ClayCard from '@clayui/card';
import classNames from 'classnames';
import BaseButton from '~/common/components/BaseButton';
import StatusTag from './StatusTag';

const getCurrentEndDate = (currentEndDate) => {
	const date = new Date(currentEndDate);
	const month = date.toLocaleDateString('default', { month: 'short' });
	const day = date.getDate();
	const year = date.getFullYear();

	return `${month} ${day}, ${year}`;
}

const ProjectCard = ({ onClick, region, sla, small, status, title }) => {

  return (
    <ClayCard className={classNames('m-0',
      {
        'project-card': !small,
        'project-card-sm': small
      }
    )} onClick={() => onClick()}>
      <ClayCard.Body className={classNames("d-flex", "h-100", "justify-content-between", {
        'flex-column': !small,
        'flex-row': small
      })}>
        <ClayCard.Description className="text-neutral-3" displayType="title" tag={small ? "h4" : "h3"}>
          {title}
        </ClayCard.Description>

        <div className={classNames("d-flex", "justify-content-between", { "align-items-end": small })}>
          <ClayCard.Description displayType="text" tag="div" title={null} truncate={false}>
            <StatusTag currentStatus={status} />

            <div className={classNames('text-paragraph-sm', 'text-neutral-5', {
              'my-1': !small,
              'sm-mb': small
            })}>
              Ends on <span className="font-weight-bold text-paragraph">{getCurrentEndDate(sla.currentEndDate)}</span>
            </div>

            <div className="text-neutral-5 text-paragraph-sm">
              {region}
            </div>
          </ClayCard.Description>
          {!small && <BaseButton appendIcon="angle-right" borderless className="p-0 text-brand-primary">
            See details
          </BaseButton>}
        </div>
      </ClayCard.Body>
    </ClayCard>
  );
};

export default ProjectCard;