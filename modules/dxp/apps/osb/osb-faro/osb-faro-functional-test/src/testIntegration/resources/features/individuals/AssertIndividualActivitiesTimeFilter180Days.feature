@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert Individual Activities time filter changes to 180 days
	As a Business User, I should be able to change individual activities time filter to 180 days

	Background: [Setup] Navigate to Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Change time filter in individual activities
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I click the "Last 30 days" dropdown in the "Individual Activities" card
		And I click the "More Preset Periods" button
		And I click the "Last 180 days" button
		Then I should see "180" columns in the bar graph table