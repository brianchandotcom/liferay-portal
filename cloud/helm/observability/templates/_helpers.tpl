{{- define "observability.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "observability.customLabels" -}}
{{- with .Values.customLabels }}
{{ toYaml . }}
{{- end }}
{{- end }}

{{- define "observability.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{- define "observability.labels" -}}
{{ include "observability.selectorLabels" . }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
component: observability
helm.sh/chart: {{ include "observability.chart" . }}
{{- include "observability.customLabels" . }}
{{- end }}

{{- define "observability.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "observability.namespace" -}}
{{- .Release.Namespace | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "observability.selectorLabels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/name: {{ include "observability.name" . }}
{{- end }}

{{- define "observability.datasourceUid" -}}
{{- .Values.datasource.uid -}}
{{- end -}}

{{- define "observability.cloudProvider" -}}
{{- .Values.cloudProvider -}}
{{- end -}}

{{- define "observability.grafana.fullname" -}}
{{- $grafanaCtx := index .Subcharts "grafana" -}}
{{- if $grafanaCtx -}}
{{- include "grafana.fullname" $grafanaCtx -}}
{{- else -}}
{{- printf "%s-grafana" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{- define "observability.grafana.internalHost" -}}
{{- $grafanaFullname := include "observability.grafana.fullname" . -}}
http://{{ $grafanaFullname }}.{{ .Release.Namespace }}.svc
{{- end -}}

{{- define "observability.dashboardHeaderHTML" -}}
<div style="padding: 16px 24px; background-color: #f8fafc; border: 1px solid #e5e7eb; border-radius: 8px; font-family: 'Inter', system-ui, sans-serif; display: flex; flex-wrap: wrap; gap: 24px; align-items: center; width: 100%; box-sizing: border-box; box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);">
    <a href="/d/central-hub/central-hub" style="background-color: #f8fafc; border: 2px solid #3274d9; border-radius: 10px; padding: 16px; text-decoration: none; display: flex; flex-direction: column; height: 100%; box-sizing: border-box;">
        <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
            <span style="font-size: 24px;">🏠</span>
        </div>
        <div style="color: #1e3a8a; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Central Hub</div>
        <div style="color: #64748b; font-size: 12px; line-height: 1.3;">Platform overview.</div>
    </a>
    <div style="margin-left: 24px;">
        <h2 style="margin: 0; color: #111827; font-size: 20px; font-weight: 800;">{{ .title }}</h2>
        <p style="margin: 4px 0 0 0; color: #4b5563; font-size: 13px;">{{ .subtitle }}</p>
    </div>
    <div style="display: flex; align-items: center; gap: 8px; margin-left: auto;">
        <span style="color: #6b7280; font-size: 13px; font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px;">Environment:</span>
        <span style="color: #111827; font-size: 14px; font-weight: 700; background: #e2e8f0; padding: 4px 10px; border-radius: 6px;">${environment:text}</span>
    </div>
</div>
{{- end -}}

{{- define "observability.centralHubHTML" -}}
<div style="padding: 32px; background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 12px; font-family: 'Inter', system-ui, sans-serif; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); position: relative; box-sizing: border-box; width: 100%;">

    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 32px; position: relative; z-index: 1; flex-wrap: wrap; gap: 16px; width: 100%;">

        <div style="height: 30px; display: flex; align-items: center; flex: 1 1 0;">
            <img src="https://www-cdn.liferay.com/documents/d/guest/liferay-logo" style="height: 35px; width: auto;" alt="Liferay Logo">
        </div>

        <div style="position: absolute; left: 50%; transform: translateX(-50%); text-align: center; white-space: nowrap;">
            <h2 style="margin: 0; font-size: 28px; font-weight: 800; color: #1e3a8a; letter-spacing: -0.5px;">Central Hub</h2>
        </div>

        <div style="flex: 1 1 0;"></div>
    </div>

    <div style="display: flex; flex-wrap: wrap; gap: 32px; width: 100%; position: relative; z-index: 1;">

        <div style="width: 49%; flex: 1 1 40%; min-width: 500px; box-sizing: border-box;">
            <div style="margin-bottom: 24px; border-left: 6px solid #3274d9; padding-left: 16px;">
                <h1 style="margin: 0; color: #111827; font-size: 26px; font-weight: 800; letter-spacing: -0.5px;">
                    Environment Resources
                </h1>
                <p style="margin: 4px 0 0 0; color: #4b5563; font-size: 14px; line-height: 1.5;">
                    Operator-configured external systems.
                </p>
            </div>

            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 12px; width: 100%;">
                <a href="{{ .gitHref }}" target="_blank" style="{{ .gitTileStyle }}">
                    <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                        <img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png" width="28" height="28" alt="GitHub">
                    </div>
                    <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">GitHub Source</div>
                    <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">{{ .gitDescription }}</div>
                </a>

                <a href="{{ .argocdHref }}" target="_blank" style="{{ .argocdTileStyle }}">
                    <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                        <img src="https://raw.githubusercontent.com/argoproj/argo-workflows/master/docs/assets/logo.png" width="28" height="28" alt="ArgoCD">
                    </div>
                    <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">ArgoCD</div>
                    <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">{{ .argocdDescription }}</div>
                </a>

                <a href="{{ .argoWfHref }}" target="_blank" style="{{ .argoWfTileStyle }}">
                    <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                        <img src="https://raw.githubusercontent.com/argoproj/argo-workflows/master/docs/assets/logo.png" width="28" height="28" alt="Argo Workflows">
                    </div>
                    <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Argo Workflows</div>
                    <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">{{ .argoWfDescription }}</div>
                </a>
            </div>
        </div>

        <div style="width: 49%; flex: 1 1 40%; min-width: 500px; box-sizing: border-box;">
            <div style="margin-bottom: 24px; border-left: 6px solid #22c55e; padding-left: 16px;">
                <h1 style="margin: 0; color: #111827; font-size: 26px; font-weight: 800; letter-spacing: -0.5px;">
                    Monitoring & Status
                </h1>
                <p style="margin: 4px 0 0 0; color: #4b5563; font-size: 14px; line-height: 1.5;">
                    Real-time observability and infrastructure health.
                </p>
            </div>

            <div style="display: flex; gap: 12px; width: 100%;">

                <div style="width: 49%; flex: 1 1 49%; display: flex; flex-direction: column; gap: 12px;">

                    <a href="/alerting/list" style="background-color: #fef2f2; border: 2px solid #dc2626; border-radius: 10px; padding: 16px; text-decoration: none; display: flex; flex-direction: column; height: 100%; box-sizing: border-box;">
                        <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                            <span style="font-size: 24px;">🔔</span>
                        </div>
                        <div style="color: #991b1b; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Alerts</div>
                        <div style="color: #b91c1c; font-size: 12px; line-height: 1.3;">Active firing & rules.</div>
                    </a>

                    <a href="/d/kubernetes-workloads/kubernetes-workloads" style="background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 10px; padding: 16px; text-decoration: none; display: flex; flex-direction: column; height: 100%; box-sizing: border-box;">
                        <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                            <span style="font-size: 24px;">📦</span>
                        </div>
                        <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Workloads</div>
                        <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">Usage & pods.</div>
                    </a>

                    <a href="/d/backups/backups" style="background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 10px; padding: 16px; text-decoration: none; display: flex; flex-direction: column; height: 100%; box-sizing: border-box;">
                        <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                            <span style="font-size: 24px;">💾</span>
                        </div>
                        <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Backups</div>
                        <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">Data recovery.</div>
                    </a>

                </div>

                <div style="width: 49%; flex: 1 1 49%; display: flex; flex-direction: column; gap: 12px;">

                    <a href="/d/databases/databases" style="background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 10px; padding: 16px; text-decoration: none; display: flex; flex-direction: column; height: 100%; box-sizing: border-box;">
                        <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                            <span style="font-size: 24px;">🛢️</span>
                        </div>
                        <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Databases</div>
                        <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">DB health.</div>
                    </a>

                    <a href="/d/load-balancers/load-balancers" style="background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 10px; padding: 16px; text-decoration: none; display: flex; flex-direction: column; height: 100%; box-sizing: border-box;">
                        <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                            <span style="font-size: 24px;">⚖️</span>
                        </div>
                        <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Load Balancers</div>
                        <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">Traffic status.</div>
                    </a>

                </div>

            </div>
        </div>
    </div>

    <div style="margin-top: 32px; padding-top: 16px; border-top: 1px solid #f3f4f6; display: flex; justify-content: space-between; align-items: center; position: relative; z-index: 1; flex-wrap: wrap; gap: 16px; width: 100%;">
        <div style="color: #9ca3af; font-size: 10px; font-weight: 700; text-transform: uppercase; letter-spacing: 0.5px;">
            <span style="background: #e5e7eb; color: #4b5563; padding: 2px 6px; border-radius: 4px; margin-right: 6px;">v${liferay_chart_version}</span>
            © 2026 Liferay Inc.
        </div>
        <div style="display: flex; gap: 16px;">
            <a href="https://learn.liferay.com/w/dxp/self-hosted-installation-and-upgrades/setting-up-liferay/activating-liferay-dxp#docker-and-cloud-native-deployments" style="color: #3274d9; font-size: 11px; font-weight: 700; text-decoration: none;">Support</a>
            <a href="https://liferay.atlassian.net/servicedesk/customer/portals" style="color: #3274d9; font-size: 11px; font-weight: 700; text-decoration: none;">Docs</a>
        </div>
    </div>

</div>
{{- end -}}

{{- define "observability.awsBackupHTML" -}}
<div style="padding: 32px; background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 12px; font-family: 'Inter', system-ui, sans-serif; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05); box-sizing: border-box; width: 100%;">

    <div style="margin-bottom: 24px; border-left: 6px solid #ff9900; padding-left: 16px;">
        <h1 style="margin: 0; color: #111827; font-size: 26px; font-weight: 800; letter-spacing: -0.5px;">AWS Backup</h1>
        <p style="margin: 4px 0 0 0; color: #4b5563; font-size: 14px; line-height: 1.5;">On AWS, backups are managed by the native AWS Backup service. Recovery points, backup jobs, and restore history are tracked in the AWS Backup console rather than in Grafana.</p>
    </div>

    <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 16px; width: 100%;">

        <a href="https://{{ .region }}.console.aws.amazon.com/backup/home?region={{ .region }}#/dashboard" target="_blank" style="background-color: #ffffff; border: 2px solid #ff9900; border-radius: 10px; padding: 20px; text-decoration: none; display: flex; flex-direction: column; box-sizing: border-box;">
            <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                <span style="font-size: 24px;">🗄️</span>
            </div>
            <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">AWS Backup Dashboard</div>
            <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">Jobs, recovery points & status ({{ .region }}).</div>
        </a>

        <a href="https://{{ .region }}.console.aws.amazon.com/backup/home?region={{ .region }}#/backupvaults" target="_blank" style="background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 10px; padding: 20px; text-decoration: none; display: flex; flex-direction: column; box-sizing: border-box;">
            <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                <span style="font-size: 24px;">🔒</span>
            </div>
            <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Backup Vaults</div>
            <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">Stored recovery points by vault.</div>
        </a>

        <a href="https://{{ .region }}.console.aws.amazon.com/backup/home?region={{ .region }}#/jobs" target="_blank" style="background-color: #ffffff; border: 1px solid #e5e7eb; border-radius: 10px; padding: 20px; text-decoration: none; display: flex; flex-direction: column; box-sizing: border-box;">
            <div style="margin-bottom: 8px; height: 28px; display: flex; align-items: center;">
                <span style="font-size: 24px;">📋</span>
            </div>
            <div style="color: #111827; font-weight: 800; font-size: 16px; margin-bottom: 2px;">Backup &amp; Restore Jobs</div>
            <div style="color: #6b7280; font-size: 12px; line-height: 1.3;">Recent job history & failures.</div>
        </a>

    </div>
</div>
{{- end -}}
