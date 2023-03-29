@spira_Order @Order @Segments @List @Profile @team_FARO @priority_3
Feature: Order the Segment Profile Engagement List
	As an Business User, I should be able to order a Segment Profile's Engagement List

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Order the Segment Profile's Engagement List
		Given I click the "Membership" tab
		And I click the "Segment Engagement Score" card tab
		When I sort the table by the "Name" column header in descending order
		Then I should see the users sorted in descending alphabetical order
		When I sort the table by the "Name" column header in ascending order
		Then I should see the users sorted in ascending alphabetical order