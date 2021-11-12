import ClayCard from '@clayui/card';
import RoundedSkeleton from '~/common/components/skeleton/rounded-skeleton';
import TextSkeleton from '~/common/components/skeleton/text-skeleton';

const ProjectCard = () => {

  return (
    <ClayCard className="m-0 project-card">
      <ClayCard.Body className="d-flex flex-column h-100 justify-content-between">
        <ClayCard.Description displayType="title">
          <TextSkeleton height={32} width={136} />
        </ClayCard.Description>

        <div className="d-flex justify-content-between">
          <ClayCard.Description displayType="text" tag="div" title={null} truncate={false}>
            <RoundedSkeleton height={20} width={49} />

            <TextSkeleton className="my-1" height={24} width={84} />

          </ClayCard.Description>

          <TextSkeleton className="my-1" height={24} width={75} />

        </div>
      </ClayCard.Body>
    </ClayCard>
  );
};

export default ProjectCard;