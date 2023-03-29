@spira_Segments @Segments @Profile @team_FARO @priority_3
Feature: Segment Composition shows Active and Known individuals
	As a Business User, I should be able to assert Active and Known individuals

	Background: [Setup] Navigate to Segments page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Assert segment active and known individuals
		Given I click "engineers" in the table
		Then I should see segment composition with "3" Active individuals
		And I should see segment composition with "3" Known individuals