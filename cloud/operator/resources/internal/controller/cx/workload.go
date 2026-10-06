package cx

import (
	"fmt"
	"slices"

	corev1 "k8s.io/api/core/v1"
)

const (
	MountPathDxpMetadata     = "/etc/liferay/lxc/dxp-metadata"
	MountPathExtInitMetadata = "/etc/liferay/lxc/ext-init-metadata"
)

func validatePodTemplate(
	configMapName string,
	environmentVariableName string,
	mountPath string,
	podTemplate *corev1.PodTemplateSpec,
) []string {
	volumeIndex := slices.IndexFunc(
		podTemplate.Spec.Volumes,
		func(volume corev1.Volume) bool {
			return (volume.ConfigMap != nil) && (configMapName == volume.ConfigMap.Name)
		},
	)

	if volumeIndex < 0 {
		return []string{fmt.Sprintf("No volume holds ConfigMap %q.", configMapName)}
	}

	volumeName := podTemplate.Spec.Volumes[volumeIndex].Name

	mounted := false

	for _, container := range podTemplate.Spec.Containers {
		if !slices.ContainsFunc(
			container.VolumeMounts,
			func(volumeMount corev1.VolumeMount) bool {
				return (mountPath == volumeMount.MountPath) && (volumeMount.Name == volumeName)
			},
		) {
			continue
		}

		mounted = true

		if slices.ContainsFunc(
			container.Env,
			func(environmentVariable corev1.EnvVar) bool {
				return (environmentVariable.Name == environmentVariableName) && (environmentVariable.Value == mountPath)
			},
		) {

			return nil
		}
	}

	if !mounted {
		return []string{
			fmt.Sprintf("No container mounts volume %q at %q.", volumeName, mountPath),
		}
	}

	return []string{
		fmt.Sprintf(
			"No container mounts volume %q and sets %s to %q.", volumeName,
			environmentVariableName, mountPath,
		),
	}
}
