@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert 180 Day time period filter in Sites
    As a Business User, I should be able to change time period filter to 180 days.

    Background: [Setup] Navigate to Sites
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page

    Scenario: Change time filter in Site Actvities
        Given I go to the "Sites" page
        When I click the "Last 30 days" dropdown in the "LIFERAY-DATASOURCE-FARO-EXAMPLE Activities" card
        And I click the "More Preset Periods" button
        And I click the "Last 180 days" button
        Then I should see "180" columns in the bar graph table